function apply(doubleList) {
    var array = [(doubleList[0] + doubleList[1])/2];
    return Java.to(array, "java.util.List");
}