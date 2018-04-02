package org.docx4j.spring.boot;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.docx4j.fonts.BestMatchingMapper;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.spring.boot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

@Configuration
@ConditionalOnProperty(prefix = Docx4jProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Docx4jProperties.class })
public class Docx4jAutoConfiguration implements ResourceLoaderAware {
	
	protected static Logger LOG = LoggerFactory.getLogger(Docx4jAutoConfiguration.class);
	private ResourceLoader resourceLoader;
	@Autowired
	private Docx4jProperties properties;
	
	@Bean
	@ConditionalOnMissingBean
	public Mapper fontMapper() {
		
		// Set up font mapper (optional)
		
		// example of mapping font Times New Roman which doesn't have certain Arabic glyphs
		// eg Glyph "ي" (0x64a, afii57450) not available in font "TimesNewRomanPS-ItalicMT".
		// eg Glyph "ج" (0x62c, afii57420) not available in font "TimesNewRomanPS-ItalicMT".
		// to a font which does
		PhysicalFonts.get("Arial Unicode MS"); 
		
		Map<String, String> fontMap = properties.getFontMapper();
		if(CollectionUtils.isEmpty(fontMap)) {
			/*
			 * This mapper uses Panose to guess the physical font which is a closest fit for the font used in the document.
			 * （这个映射器使用Panose算法猜测最适合这个文档使用的物理字体。）
			 * 
			 * Panose是一种依照字体外观来进行分类的方法。我们可以通过PANOSE体系将字体的外观特征进行整理，并且与其它字体归类比较。
			 * Panose的原形在1985年由Benjamin Bauermeister开发，当时一种字体由7位16进制数字定义，现在则发展为10位，也就是字体的十种特征。这每一位数字都给出了它定义的一种视觉外观的量度，如笔划的粗细或是字体衬线的样式等。
			 * Panose定义的范围：Latin Text，Latin Script，Latin Decorative，Iconographic，Japanese Text，Cyrillic Text，Hebrew。
			 * 	
			 * It is most likely to be suitable on Linux or OSX systems which don't have Microsoft's fonts installed.
			 * （它很可能适用于没有安装Microsoft字体的Linux或OSX系统。）
			 * 
			 * 1、获取Microsoft字体我们需要这些：a.在Microsoft平台上，嵌入PDF输出; b. docx4all - 所有平台 - 填充字体下拉列表
			 * setupMicrosoftFontFilenames();
			 * 2、 自动检测系统上可用的字体
			 * PhysicalFonts.discoverPhysicalFonts();
			 * 
			 */
			Mapper fontMapper = new BestMatchingMapper();
			defaultMapper(fontMapper);
			return fontMapper;
			
		} else {
			
			/*
			 * 
			 * This mapper automatically maps document fonts for which the exact font is physically available.  
		     * Think of this as an identity mapping.  For  this reason, it will work best on Windows, or a system on 
		     * which Microsoft fonts have been installed.
		     * （此映射器自动映射确切可用的文档字体，将此视为标识映射；基于这个原因，它在Windows系统或安装了微软字体库的系统运行的更好。）
		     * You can manually add your own additional mappings if you wish.
		     * 如果需要，你可以手动添加自己的字体映射
			 * 
			 * 1、 自动检测系统上可用的字体
			 * PhysicalFonts.discoverPhysicalFonts();
			 * 
			 */
			Mapper fontMapper = new IdentityPlusMapper();
			//遍历自定义的字体库信息
			Iterator<Entry<String, String>> ite = fontMap.entrySet().iterator();
			while (ite.hasNext()) {
				
				Entry<String, String> entry = ite.next();
				
				try {
					//获取字体库
					PhysicalFont physicalFont = PhysicalFonts.get(entry.getKey());
					if(physicalFont == null){
						//加载字体文件（解决linux环境下无中文字体问题）
						Resource resource = resourceLoader.getResource(entry.getValue());
						if(resource.exists()) {
							PhysicalFonts.addPhysicalFonts(entry.getKey(), resource.getURL());
						}
						LOG.debug("Add PhysicalFont " +  entry.getKey() );
					}
					
					//设置字体名对应的字体库
					fontMapper.put(entry.getKey(), physicalFont );
					//设置字体别名对应的字体库，支持多个别名
					String fontAliasArr = properties.getFontAliasMapper().get(entry.getKey());
					if(StringUtils.hasText(fontAliasArr)) {
						for (String fontAlias : StringUtils.tokenizeToStringArray(fontAliasArr)) {
							fontMapper.put(fontAlias, physicalFont );
						}
					}
					
				} catch (Exception e) {
					LOG.debug("Add PhysicalFont Fail : Ingore" );
				}
				
			}
			
			defaultMapper(fontMapper);
			
			return fontMapper;
		}
	}
	
	protected void defaultMapper(Mapper fontMapper) {

		//进行中文字体兼容处理
		fontMapper.put("微软雅黑",PhysicalFonts.get("Microsoft Yahei"));
        fontMapper.put("黑体",PhysicalFonts.get("SimHei"));
        fontMapper.put("楷体",PhysicalFonts.get("KaiTi"));
        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
        fontMapper.put("宋体",PhysicalFonts.get("SimSun"));
        fontMapper.put("宋体扩展",PhysicalFonts.get("simsun-extB"));
        fontMapper.put("新宋体",PhysicalFonts.get("NSimSun"));
        fontMapper.put("仿宋",PhysicalFonts.get("FangSong"));
        fontMapper.put("仿宋_GB2312",PhysicalFonts.get("FangSong_GB2312"));
        fontMapper.put("幼圆",PhysicalFonts.get("YouYuan"));
        fontMapper.put("华文宋体",PhysicalFonts.get("STSong"));
        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
        fontMapper.put("华文中宋",PhysicalFonts.get("STZhongsong"));
        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
        
	}
	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
}
