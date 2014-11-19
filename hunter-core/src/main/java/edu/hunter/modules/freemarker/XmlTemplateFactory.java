package edu.hunter.modules.freemarker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.hunter.modules.utils.XmlHelper;
import freemarker.cache.StringTemplateLoader;

/**
 * 
 * @author Woo Cupid
 * @date 2013年10月30日
 * @version $Revision$
 */
public class XmlTemplateFactory implements FactoryBean<StringTemplateLoader>, InitializingBean {

	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	private String[] locations;

	private StringTemplateLoader templateLoader = new StringTemplateLoader();

	// XXX add a refresh configuration
	@Override
	public void afterPropertiesSet() throws Exception {
		for (String path : locations) {
			Resource r = resourceLoader.getResource(path);
			List<XmlTemplate> templates = getTemplates(r.getFile());
			for (XmlTemplate xmlTemplate : templates) {
				templateLoader.putTemplate(xmlTemplate.getName(), xmlTemplate.getTemplate(),
						xmlTemplate.getLastModified());
			}
		}
	}

	public List<XmlTemplate> getTemplates(File file) {
		List<XmlTemplate> result = new ArrayList<XmlTemplate>();
		if (file.isFile()) {
			List<XmlTemplate> templates = XmlHelper.fromXML(file, XmlTemplate.class);
			for (XmlTemplate xmlTemplate : templates) {
				xmlTemplate.setLastModified(file.lastModified());
			}
			result.addAll(templates);
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				result.addAll(getTemplates(f));
			}
		}

		return result;
	}

	@Override
	public StringTemplateLoader getObject() throws Exception {
		return templateLoader;
	}

	@Override
	public Class<StringTemplateLoader> getObjectType() {
		return StringTemplateLoader.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String[] getLocations() {
		return locations;
	}

	public void setLocations(String[] locations) {
		this.locations = locations;
	}

	public StringTemplateLoader getTemplateLoader() {
		return templateLoader;
	}

	public void setTemplateLoader(StringTemplateLoader templateLoader) {
		this.templateLoader = templateLoader;
	}

	@XStreamAlias(value = "XmlTemplate")
	public static class XmlTemplate {
		private String name;
		private String template;
		private long lastModified;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public long getLastModified() {
			return lastModified;
		}

		public void setLastModified(long lastModified) {
			this.lastModified = lastModified;
		}

		@Override
		public String toString() {
			return "XmlTemplate [name=" + name + ", template=" + template + ", lastModified=" + lastModified + "]";
		}

	}
}
