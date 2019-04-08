package hu.kleatech.jigsaw.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StaticMap<T> {
    public T first;
    public T second;
    public T third;
    public T forth;
    public T fifth;
    public T sixth;
    public T seventh;
    public T eighth;
    public T ninth;
    public T tenth;
    public T eleventh;
    public T twelfth;
    public T thirteenth;
    public T fourteenth;
    public T fifteenth;
    public T sixteenth;
    public T seventeenth;
    public T eighteenth;
    public T nineteenth;
    public T twentieth;

    public T getFirst() {
        return first;
    }
    public void setFirst(T first) {
        this.first = first;
    }
    public T getSecond() {
        return second;
    }
    public void setSecond(T second) {
        this.second = second;
    }
    public T getThird() {
        return third;
    }
    public void setThird(T third) {
        this.third = third;
    }
    public T getForth() {
        return forth;
    }
    public void setForth(T forth) {
        this.forth = forth;
    }
    public T getFifth() {
        return fifth;
    }
    public void setFifth(T fifth) {
        this.fifth = fifth;
    }
    public T getSixth() {
        return sixth;
    }
    public void setSixth(T sixth) {
        this.sixth = sixth;
    }
    public T getSeventh() {
        return seventh;
    }
    public void setSeventh(T seventh) {
        this.seventh = seventh;
    }
    public T getEighth() {
        return eighth;
    }
    public void setEighth(T eighth) {
        this.eighth = eighth;
    }
    public T getNinth() {
        return ninth;
    }
    public void setNinth(T ninth) {
        this.ninth = ninth;
    }
    public T getTenth() {
        return tenth;
    }
    public void setTenth(T tenth) {
        this.tenth = tenth;
    }
    public T getEleventh() {
        return eleventh;
    }
    public void setEleventh(T eleventh) {
        this.eleventh = eleventh;
    }
    public T getTwelfth() {
        return twelfth;
    }
    public void setTwelfth(T twelfth) {
        this.twelfth = twelfth;
    }
    public T getThirteenth() {
        return thirteenth;
    }
    public void setThirteenth(T thirteenth) {
        this.thirteenth = thirteenth;
    }
    public T getFourteenth() {
        return fourteenth;
    }
    public void setFourteenth(T fourteenth) {
        this.fourteenth = fourteenth;
    }
    public T getFifteenth() {
        return fifteenth;
    }
    public void setFifteenth(T fifteenth) {
        this.fifteenth = fifteenth;
    }
    public T getSixteenth() {
        return sixteenth;
    }
    public void setSixteenth(T sixteenth) {
        this.sixteenth = sixteenth;
    }
    public T getSeventeenth() {
        return seventeenth;
    }
    public void setSeventeenth(T seventeenth) {
        this.seventeenth = seventeenth;
    }
    public T getEighteenth() {
        return eighteenth;
    }
    public void setEighteenth(T eighteenth) {
        this.eighteenth = eighteenth;
    }
    public T getNineteenth() {
        return nineteenth;
    }
    public void setNineteenth(T nineteenth) {
        this.nineteenth = nineteenth;
    }
    public T getTwentieth() {
        return twentieth;
    }
    public void setTwentieth(T twentieth) {
        this.twentieth = twentieth;
    }

    public List<T> getAll() { 
        return stream().collect(Collectors.toList());
    }
        
    public Stream<T> stream() { 
        return Stream.of(
            first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth,
            eleventh, twelfth, thirteenth, fourteenth, fifteenth,
            sixteenth, seventeenth, eighteenth, nineteenth, twentieth)
            .filter(Objects::nonNull);
    }
}
