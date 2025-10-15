package musics;


class MusicClass implements Music{
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
