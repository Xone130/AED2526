package musics;

import java.io.Serializable;

public interface Music extends Serializable {
    /**
     *
     * @return name of the music
     */
    String getName();

    /**
     *
     * @return name of the singer
     */
    String getSinger();
}
