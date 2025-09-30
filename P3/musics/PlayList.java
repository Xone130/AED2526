package musics;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface PlayList extends Serializable {

    /**
     *  insert the given song at the given position in the playlist
     * @param position
     * @param name
     * @param author
     */
    void insert(int position, String name,String author);

    /**
     * insert the given music at first position in the playlist
     * @param name
     * @param author
     */
    void insertFirst(String name,String author);

    /**
     * insert the given music at last position in the playlist
     * @param name
     * @param author
     */
    void insertLast(String name,String author);

    /**
     *
     * @return iterator to all music (from first to last)
     */
    Iterator<Music> iterator();

    /**
     * remove the music at last position in the playlist
     * @return music at last position in the playlist
     *
     */
    Music removeLast();

    /**
     * remove the music at first position in the playlist
     * @return music at first position in the playlist
     */
    Music removeFirst();

    /**
     * remove the music at the given position in the playlist
     * @return music at the given position in the playlist
     */
    Music remove(int position);

    /**
     * @return music at last position in the playlist
     */
    Music getLast();

    /**
     * @return music at first position in the playlist
     */
    Music getFirst();

    /**
     * @return music at the given position in the playlist
     */
    Music get(int position);

    /**
     *
     * @return iterator to all music (from last to first)
     */
    TwoWayIterator<Music> TwoWayIterator();

    /**
     *
     * @param singer
     * @return iterator to all music of a given singer (from first to last)
     */
    Iterator<Music> allSinger(String singer);

}
