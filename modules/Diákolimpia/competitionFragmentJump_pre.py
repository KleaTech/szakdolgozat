def apply(doubleList):
    from java.util import Arrays
    array = [max(doubleList[0], doubleList[1], doubleList[2]),
             max(doubleList[3], doubleList[4], doubleList[5])]
    return Arrays.asList(array)
