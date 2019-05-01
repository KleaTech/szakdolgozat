function apply(doubleList) {
    var array = [Math.max(doubleList[0], doubleList[1], doubleList[2]),
                 Math.max(doubleList[3], doubleList[4], doubleList[5])]
    return Java.to(array, "java.util.List");
}
