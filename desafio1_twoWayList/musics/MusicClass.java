package musics;

import java.io.Serializable;

class MusicClass implements Music, Serializable {
    private String name;
    private String author;

    public MusicClass(String name,String author) {
        this.name=name;
        this.author=author;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSinger() {
        return author;
    }
}
