package xyz.auriium.tick.centralized;

import xyz.auriium.tick.utils.Stoppable;

/**
 * Manages resources for the tick system
 */
public interface ResourceManager extends Stoppable {

    void submitContainer(String id, boolean val);
    void destroyContainer(String id);

    /**
     * Inserts an image into the manager for later cleanup
     * @param imageName the name of the image
     */
    void submitImage(String imageName);
    void destroyImage(String imageName);

}
