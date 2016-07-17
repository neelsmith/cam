/*
Parse composite file from Latin Library, convert to 2-column format.

Usage: groovy parseBg.groovy FILE
 */


File f = new File(args[0])

String baseUrn = "urn:cts:latinLit:stoa0069.stoa001.hc:"

def lines = f.readLines()

def currBook = ""
lines.each { l ->

  if (l ==~ /^[1-8]$/) {
    currBook = l
  } else if ((l.size() > 0) && (l[0] == "[")) {
    def ref = l.replaceFirst("\\].+", "").replaceFirst("\\[","")
    def txt = l.replaceFirst("\\[","").replaceFirst(/[^\\]]+/,"")
  txt.findAll  (/([0-9]+) ([^0-9]+)/) { wholematch, sectref, secttxt ->
      println "${baseUrn}${currBook}.${ref}.${sectref}\t" + secttxt
  }
  
  /*
  def sectmatcher = txt =~ "( [0-9]+ )([^0-9]+)"
  if (sectmatcher.getCount()) {
    sectmatcher.each { wholematch, sectref, secttxt ->
      println wholematch
      println "\t" + sectref + " " + secttxt
    }
    }*/


  /*
  def sections = txt.split(/[0-9]+/)
  sections.eachWithIndex { s, i ->
    if (i > 0) {
    println "${baseUrn}${currBook}.${ref}.${i}\t" + s
    }
    }
  */
  
  }
  
}