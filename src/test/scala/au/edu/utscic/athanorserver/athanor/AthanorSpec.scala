package au.edu.utscic.athanorserver.athanor

import au.edu.utscic.athanorserver.UnitSpec
import au.edu.utscic.athanorserver.data.RhetoricalTypes.{Dependency, Node, ParsedSentence}
import org.skyscreamer.jsonassert.JSONAssert

import scala.collection.immutable.SortedMap

/**
  * Created by andrew@andrewresearch.net on 19/7/17.
  */
class AthanorSpec extends UnitSpec {

  val localpath = "/Users/andrew/Documents/development/_projects/CIC-Current/athanor-server/scripts"
  val demoJson = Athanor.demoFile
  val rhetoricalMoves = List("ContrastStance", "SubjectAnalysis", "contrast", "context1", "challenge1", "temporality", "StanceAnalysis", "challenge")
  val parsedSent:ParsedSentence = ( SortedMap( 0 -> Node(0,"ROOT",None,None,None,None,None,None), 1 -> Node(1,"RB",Some("Oddly"),Some("oddly"),Some("O"),Some("PER0"),Some(0),Some(5)), 2 -> Node(2,"RB",Some("enough"),Some("enough"),Some("O"),Some("PER0"),Some(6),Some(12)), 3 -> Node(3,"PRP",Some("I"),Some("I"),Some("O"),Some("PER0"),Some(13),Some(14)), 4 -> Node(4,"VBD",Some("found"),Some("find"),Some("O"),Some("PER0"),Some(15),Some(20)), 5 -> Node(5,"PRP",Some("it"),Some("it"),Some("O"),Some("PER0"),Some(21),Some(23)), 6 -> Node(6,"RB",Some("quite"),Some("quite"),Some("O"),Some("PER0"),Some(24),Some(29)), 7 -> Node(7,"VBG",Some("empowering"),Some("empower"),Some("O"),Some("PER0"),Some(30),Some(40)), 8 -> Node(8,"TO",Some("to"),Some("to"),Some("O"),Some("PER0"),Some(41),Some(43)), 9 -> Node(9,"VB",Some("hear"),Some("hear"),Some("O"),Some("PER0"),Some(44),Some(48)), 10 -> Node(10,"NNP",Some("Natalia"),Some("Natalia"),Some("PERSON"),Some("PER0"),Some(49),Some(56)), 11 -> Node(11,"NN",Some("state"),Some("state"),Some("O"),Some("PER0"),Some(57),Some(62)), 12 -> Node(12,"DT",Some("the"),Some("the"),Some("O"),Some("PER0"),Some(63),Some(66)), 13 -> Node(13,"VBG",Some("following"),Some("follow"),Some("O"),Some("PER0"),Some(67),Some(76)), 14 -> Node(14,"``",Some("``"),Some("``"),Some("O"),None,Some(77),Some(79)), 15 -> Node(15,"JJ",Some("Real"),Some("real"),Some("O"),Some("PER1"),Some(79),Some(83)), 16 -> Node(16,"NN",Some("life"),Some("life"),Some("O"),Some("PER1"),Some(84),Some(88)), 17 -> Node(17,"NNS",Some("dilemmas"),Some("dilemma"),Some("O"),Some("PER1"),Some(89),Some(97)), 18 -> Node(18,"RB",Some("often"),Some("often"),Some("O"),Some("PER1"),Some(98),Some(103)), 19 -> Node(19,"JJ",Some("present"),Some("present"),Some("DATE"),Some("PER1"),Some(104),Some(111)), 20 -> Node(20,"NNS",Some("choices"),Some("choice"),Some("O"),Some("PER1"),Some(112),Some(119)), 21 -> Node(21,"IN",Some("between"),Some("between"),Some("O"),Some("PER1"),Some(120),Some(127)), 22 -> Node(22,"RB",Some("equally"),Some("equally"),Some("O"),Some("PER1"),Some(128),Some(135)), 23 -> Node(23,"JJ",Some("unfavorable"),Some("unfavorable"),Some("O"),Some("PER1"),Some(136),Some(147)), 24 -> Node(24,"CC",Some("or"),Some("or"),Some("O"),Some("PER1"),Some(148),Some(150)), 25 -> Node(25,"JJ",Some("disagreeable"),Some("disagreeable"),Some("O"),Some("PER1"),Some(151),Some(163)), 26 -> Node(26,"NNS",Some("alternatives"),Some("alternative"),Some("O"),Some("PER1"),Some(164),Some(176)), 27 -> Node(27,".",Some("."),Some("."),Some("O"),Some("PER1"),Some(176),Some(177))),  List("ROOT", List("S", List("ADVP", List("RB",1), List("RB",2)), List("NP", List("PRP",3)), List("VP", List("VBD",4), List("NP", List("PRP",5)), List("ADVP", List("RB",6), List("VP", List("VBG",7), List("S", List("VP", List("TO",8), List("VP", List("VB",9), List("S", List("NP", List("NNP", 10)), List("NP", List("NP", List("NN", 11), List("DT", 12)), List("PP", List("VBG", 13), List("NP", List("`", 14), List("NP", List("JJ", 15), List("NN", 16), List("NNS", 17)), List("ADVP", List("RB", 18), List("NP", List("JJ", 19), List("NNS", 20))), List("PP", List("IN", 21), List("NP", List("NP", List("RB", 22), List("JJ", 23)), List("CC", 24), List("NP", List("JJ", 25), List("NNS", 26)))))))))))))), List(".", 27))), List(Dependency("root",0,4), Dependency("advmod",2,1), Dependency("advmod",4,2), Dependency("nsubj",4,3), Dependency("dobj",4,5), Dependency("advmod",4,6), Dependency("dep",6,7), Dependency("mark",9,8), Dependency("xcomp",7,9), Dependency("nsubj",11,10), Dependency("xcomp",9,11), Dependency("dep",11,12), Dependency("case",17,13), Dependency("punct",17,14), Dependency("amod",17,15), Dependency("compound",17,16), Dependency("nmod",11,17), Dependency("advmod",17,18), Dependency("amod",20,19), Dependency("nmod:npmod",18,20), Dependency("case",23,21), Dependency("advmod",23,22), Dependency("nmod",17,23), Dependency("cc",23,24), Dependency("amod",26,25), Dependency("conj",23,26), Dependency("punct",4,27)))

  behavior of "AthanorSpec"

  it should "fullPath" in {
    val fileName = "dummyFile"
    assert(Athanor.fullPath(fileName)==s"$localpath/dummyFile")
  }

  it should "parseJsonSentence" in {
    val ps = Athanor.parseJsonSentence(demoJson)
    assert(ps==parsedSent)
  }

  it should "parsedSentenceToJsonString" in {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    //Get strings
    val expected = demoJson
    val actual = Athanor.parsedSentenceToJsonString(parsedSent)
    //Check that the strings can actually be parsed into json
    val expJson = parse(expected)
    val actJson = parse(actual)
    //Check compact version of both for each element
    val exp1 = compact(render(expJson(0)))
    val act1 = compact(render(actJson(0)))
    JSONAssert.assertEquals(exp1, act1, true)
    val exp2 = compact(render(expJson(1)))
    val act2 = compact(render(actJson(1)))
    JSONAssert.assertEquals(exp2, act2, true)
    val exp3 = compact(render(expJson(2)))
    val act3 = compact(render(actJson(2)))
    JSONAssert.assertEquals(exp3, act3, true)
    //And check full non-compact versions
    JSONAssert.assertEquals(expected,actual,true)
  }

  it should "analyseParsedSentence" in {
    val parsedSent = Athanor.parseJsonSentence(Athanor.demoFile)
    val result = Athanor.analyseParsedSentence(parsedSent)
    assert(result.toSet==rhetoricalMoves.toSet) //Order doesn't matter
  }

  it should "analyseJsonSentence" in {
    val result = Athanor.analyseJson(Athanor.demoFile)
    assert(result.toSet==rhetoricalMoves.toSet) //Order doesn't matter
  }




}
