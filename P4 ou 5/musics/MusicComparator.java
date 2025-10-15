package musics;

import dataStructures.Comparator;

class MusicComparator implements Comparator<Music> {
    /**
     * Compares its two arguments for order.
     * Returns a negative integer, zero, or a positive integer as the first argument
     * is less than, equal to, or greater than the second.
     * Must ensure that:
     * signum(compare(x, y)) == -signum(compare(y, x)) for all x and y.
     * relation is transitive: ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.
     * compare(x, y)==0 implies that signum(compare(x, z))==signum(compare(y, z)) for all z.
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public int compare(Music x, Music y) {
        if (x==y) return 0;
        if (x==null) return -1;
        if (y== null) return 1;
        if ((x.getName()==null) && (y.getName()==null)) return 0;
        if (x.getName()==null) return -1;
        if (y.getName()== null) return 1;
        return x.getName().compareTo(y.getName());
    }
}
