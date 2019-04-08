function apply(doubleList) {
    var array = [(doubleList[0] + doubleList[1] + doubleList[2])/3.0, (doubleList[3] + doubleList[4] + doubleList[5])/3.0];
    return Java.to(array, "java.util.List");
}