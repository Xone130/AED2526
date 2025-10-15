package musics;

import java.io.Serializable;

public interface Music extends  Serializable {
    /**
     *
     * @return the name of the music
     */
    String getName();

    /**
     *
     * @return de name of the singer
     */
    String getSinger();
}
