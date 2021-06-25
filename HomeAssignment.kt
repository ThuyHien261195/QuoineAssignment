

fun main() {
    val points = arrayOf(intArrayOf(1, 4), intArrayOf(3, 4), intArrayOf(3, 10))
    val result = findPoint(points)
    if (result != null) {
        println("Result: (${result.get(0)}, ${result.get(1)})")
    } else {
        println("Cannot find point")
    }
}

// This method to find rest point to make a rectangle from given 3 points. 
fun findPoint(pointList: Array<IntArray>): IntArray? {
    if (pointList.size != 3) {
        return null
    }

    val vectors = ArrayList<VectorData>()

    // Get vector between two points in order point array. 
    for (i in pointList.indices) {
        val next = i + 1
        for (j in next until pointList.size) {
            val vector = getVectorBetweenTwoPoints(pointList[i], pointList[j])
            vectors.add(VectorData(vector, i, j))
        }
    }

    val rightPos = getRightAnglePos(vectors)
    if (rightPos == -1) {
        return null
    } else {
        val restPoint = pointList.filterIndexed { index, _ -> index != rightPos }
        val vector = getVectorBetweenTwoPoints(pointList[rightPos], restPoint[0])
        return intArrayOf(restPoint[1][0] + vector[0], restPoint[1][1] + vector[1])
    }
}

// This method to get vector from 2 points
fun getVectorBetweenTwoPoints(pointA: IntArray, pointB: IntArray): IntArray {
    val a = (pointB[0] - pointA[0])
    val b = (pointB[1] - pointA[1])
    return intArrayOf(a, b)
}

// This method is used to check whether 2 vectors are perpendicular
fun isPerpendicular(vectorA: IntArray, vectorB: IntArray): Boolean {
    val result = vectorA[0] * vectorB[0] + vectorA[1] * vectorB[1]
    return result == 0
}

// Find right angle point
// The method returns item's position in PointList  
fun getRightAnglePos(vectors: List<VectorData>): Int {
    for (i in vectors.indices) {
        val next = i + 1
        for (j in next until vectors.size) {
            if (isPerpendicular(vectors[i].vector, vectors[j].vector)) {
                if (vectors[i].startPos == vectors[j].startPos || vectors[i].startPos == vectors[j].endPos) {
                    return vectors[i].startPos
                } else {
                    return vectors[i].endPos
                }
            }
        }
    }
    return -1
}

// We have vector 01
// vector variable is (2, 0) 
// startPos variable is 0
// endPos variable is 1
data class VectorData (
    val vector: IntArray,
    val startPos: Int,
    val endPos: Int
)