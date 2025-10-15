package musics;

import dataStructures.Predicate;

class IsSinger implements Predicate<Music> {
    
    private String singer;

    public IsSinger(String singer){
        this.singer=singer;
    }

    public boolean check(Music elem) {
	if (elem==null || elem.getSinger()==null)
            return false;
        return elem.getSinger().equals(singer);
    }
}
