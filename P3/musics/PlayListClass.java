package musics;

import dataStructures.*;

public class PlayListClass implements PlayList {

    private TwoWayList<Music> musics;

    public PlayListClass(){
        musics= new DoublyLinkedList<>();
    }

    @Override
    public void insert(int position, String name,String author) {
        musics.add(position, new MusicClass(name,author));
    }

    @Override
    public void insertFirst(String name,String author) {
        musics.addFirst(new MusicClass(name,author));
    }

    @Override
    public void insertLast(String name,String author) {
        musics.addLast(new MusicClass(name,author));
    }

    @Override
    public Iterator<Music> iterator() {
        return musics.iterator();
    }

    @Override
    public Music removeLast() {
        return musics.removeLast();
    }

    @Override
    public Music removeFirst() {
        return musics.removeFirst();
    }

    @Override
    public Music remove(int position) {
        return musics.remove(position);
    }

    @Override
    public Music getLast() {
        return musics.getLast();
    }

    @Override
    public Music getFirst() {
        return musics.getFirst();
    }

    @Override
    public Music get(int position) {
        return musics.get(position);
    }

    @Override
    public TwoWayIterator<Music> TwoWayIterator() {
        TwoWayIterator<Music> it=musics.twoWayiterator();
        it.fullForward();
        return it;
    }

    @Override
    public Iterator<Music> allSinger(String singer) {
        return new FilterIterator<>(musics.iterator(),new IsSinger(singer));
    }

}
