/*

Reads a 2-column file consisting of URN and text content, and outputs 82XF format.
In this script column delimiter is hard-coded as tab.

Usage: groovy psgTo82XF.groovy FILE

*/


File f = new File(args[0])



// maps of URN to preceding or following URNs
def prevToNext = [:]
def nextToPrev = [:]

// previously seen URN
String prevUrn = ""

/// Read through sequence of lines once to index
// abitrary URN values to preceding and following URNs
def fileLines = f.readLines()
fileLines.each {
  def cols = it.split(/\t/)
  currUrn = cols[0]
  if (prevUrn != "") {
    prevToNext[prevUrn] = currUrn
    nextToPrev[currUrn] = prevUrn
  }
  prevUrn = currUrn
}

// Print out 82XF formatted version:
println "URN#Previous#Sequence#Next#Text"
def seq = 0
fileLines.each {
  seq++;
  def cols = it.split(/\t/)
  def urn = cols[0]
  def txt = cols[1]
  def prv = ""
  def nxt = ""
  if (nextToPrev[urn]) {
    prv = nextToPrev[urn]
  }
  if (prevToNext[urn]) {
    nxt = prevToNext[urn]
  }
  println "${urn}#${prv}#${seq}#${nxt}#${txt}"
}


