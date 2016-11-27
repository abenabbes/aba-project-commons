package aba.project.commons.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;




/**
 * Classe de rechargement des resources de spring. <br>
 * Cette classe permet d'acceder a des resources extenes a partir de son chemin.<br>
 * Les resources doivents etre de la forme suivantes :<br>
 * <ul>
 * <li> file:c:/chemin/file.txt</li>
 * <li>classpath:file.txt</li>
 * <li>http://localhost.fr/file.txt</li>
 *</ul>
 * 
 * @author ali
 *
 */
public class ResourcesLoaderService implements ResourceLoaderAware {

	//______ATTRIBUTS
	/** Logger */
	private static Logger LOGGER = LoggerFactory.getLogger(ResourcesLoaderService.class);
		
	/**Chargeur des resources*/
	private ResourceLoader resourceLoader;
	
	
	//________METHODES
	public Resource recuperationResource(String path) {
		return this.getResource(path);
	}
	
	//________GETTER && SETTER
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}

}
