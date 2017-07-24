package au.edu.utscic.athanorserver

import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations._
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation

import scala.collection.immutable.SortedMap

/**
  * Created by andrew@andrewresearch.net on 20/7/17.
  */
object TestData {

  val annotationKeys = Set[Class[_]](classOf[TextAnnotation], classOf[CharacterOffsetBeginAnnotation], classOf[CharacterOffsetEndAnnotation], classOf[TokensAnnotation], classOf[TokenBeginAnnotation], classOf[TokenEndAnnotation], classOf[SentenceIndexAnnotation], classOf[NumerizedTokensAnnotation], classOf[TreeAnnotation], classOf[CollapsedDependenciesAnnotation], classOf[BasicDependenciesAnnotation], classOf[CollapsedCCProcessedDependenciesAnnotation], classOf[EnhancedDependenciesAnnotation], classOf[EnhancedPlusPlusDependenciesAnnotation])

  val textA:String = "The technology should be able to parse this sentence. It should also work with this one."

  val textAlemmas1:List[String] = List("the", "technology", "should", "be", "able", "to", "parse", "this", "sentence", ".")

  val textAnodes1:LexicalNodes = SortedMap(0 -> Node(0,"ROOT",None,None,None,None,None,None), 1 -> Node(1,"DT",Some("The"),Some("the"),Some("O"),None,Some(0),Some(3)), 2 -> Node(2,"NN",Some("technology"),Some("technology"),Some("O"),None,Some(4),Some(14)), 3 -> Node(3,"MD",Some("should"),Some("should"),Some("O"),None,Some(15),Some(21)), 4 -> Node(4,"VB",Some("be"),Some("be"),Some("O"),None,Some(22),Some(24)), 5 -> Node(5,"JJ",Some("able"),Some("able"),Some("O"),None,Some(25),Some(29)), 6 -> Node(6,"TO",Some("to"),Some("to"),Some("O"),None,Some(30),Some(32)), 7 -> Node(7,"VB",Some("parse"),Some("parse"),Some("O"),None,Some(33),Some(38)), 8 -> Node(8,"DT",Some("this"),Some("this"),Some("O"),None,Some(39),Some(43)), 9 -> Node(9,"NN",Some("sentence"),Some("sentence"),Some("O"),None,Some(44),Some(52)), 10 -> Node(10,".",Some("."),Some("."),Some("O"),None,Some(52),Some(53)))

  val textAconstTree1:ConstituentTree = List("ROOT", List("S", List("NP", List("DT", "The"), List("NN", "technology")), List("VP", List("MD", "should"), List("VP", List("VB", "be"), List("ADJP", List("JJ", "able"), List("S", List("VP", List("TO", "to"), List("VP", List("VB", "parse"), List("NP", List("DT", "this"), List("NN", "sentence")))))))), List(".", ".")))

  val textAconstTreeJson1:String = """["ROOT",["S",["NP",["DT","The"],["NN","technology"]],["VP",["MD","should"],["VP",["VB","be"],["ADJP",["JJ","able"],["S",["VP",["TO","to"],["VP",["VB","parse"],["NP",["DT","this"],["NN","sentence"]]]]]]]],[".","."]]]"""

  val textAdepend1:Dependencies = List(Dependency("det",2,1), Dependency("nsubj",5,2), Dependency("nsubj:xsubj",7,2), Dependency("aux",5,3), Dependency("cop",5,4), Dependency("mark",7,6), Dependency("xcomp",5,7), Dependency("det",9,8), Dependency("dobj",7,9), Dependency("punct",5,10))

  val textAparsed1:ParsedSentence = (textAnodes1,textAconstTree1,textAdepend1)

  val textAparsed2:ParsedSentence =  (SortedMap(0 -> Node(0,"ROOT",None,None,None,None,None,None), 1 -> Node(1,"PRP",Some("It"),Some("it"),Some("O"),None,Some(54),Some(56)), 2 -> Node(2,"MD",Some("should"),Some("should"),Some("O"),None,Some(57),Some(63)), 3 -> Node(3,"RB",Some("also"),Some("also"),Some("O"),None,Some(64),Some(68)), 4 -> Node(4,"VB",Some("work"),Some("work"),Some("O"),None,Some(69),Some(73)), 5 -> Node(5,"IN",Some("with"),Some("with"),Some("O"),None,Some(74),Some(78)), 6 -> Node(6,"DT",Some("this"),Some("this"),Some("O"),None,Some(79),Some(83)), 7 -> Node(7,"CD",Some("one"),Some("one"),Some("NUMBER"),None,Some(84),Some(87)), 8 -> Node(8,".",Some("."),Some("."),Some("O"),None,Some(87),Some(88))),List("ROOT", List("S", List("NP", List("PRP", "It")), List("VP", List("MD", "should"), List("ADVP", List("RB", "also")), List("VP", List("VB", "work"), List("PP", List("IN", "with"), List("NP", List("DT", "this"), List("CD", "one"))))), List(".", "."))),List(Dependency("nsubj",4,1), Dependency("aux",4,2), Dependency("advmod",4,3), Dependency("case",7,5), Dependency("det",7,6), Dependency("nmod:with",4,7), Dependency("punct",4,8)))

  val jsonString:String = """[{"0":{"POS":"ROOT","id":0},"1":{"id":1,"surface":"Oddly","left":0,"lemma":"oddly","right":5,"POS":"RB","NER":"O","Speaker":"PER0"},"2":{"id":2,"surface":"enough","left":6,"lemma":"enough","right":12,"POS":"RB","NER":"O","Speaker":"PER0"},"3":{"id":3,"surface":"I","left":13,"lemma":"I","right":14,"POS":"PRP","NER":"O","Speaker":"PER0"},"4":{"id":4,"surface":"found","left":15,"lemma":"find","right":20,"POS":"VBD","NER":"O","Speaker":"PER0"},"5":{"id":5,"surface":"it","left":21,"lemma":"it","right":23,"POS":"PRP","NER":"O","Speaker":"PER0"},"6":{"id":6,"surface":"quite","left":24,"lemma":"quite","right":29,"POS":"RB","NER":"O","Speaker":"PER0"},"7":{"id":7,"surface":"empowering","left":30,"lemma":"empower","right":40,"POS":"VBG","NER":"O","Speaker":"PER0"},"8":{"id":8,"surface":"to","left":41,"lemma":"to","right":43,"POS":"TO","NER":"O","Speaker":"PER0"},"9":{"id":9,"surface":"hear","left":44,"lemma":"hear","right":48,"POS":"VB","NER":"O","Speaker":"PER0"},"10":{"id":10,"surface":"Natalia","left":49,"lemma":"Natalia","right":56,"POS":"NNP","NER":"PERSON","Speaker":"PER0"},"11":{"id":11,"surface":"state","left":57,"lemma":"state","right":62,"POS":"NN","NER":"O","Speaker":"PER0"},"12":{"id":12,"surface":"the","left":63,"lemma":"the","right":66,"POS":"DT","NER":"O","Speaker":"PER0"},"13":{"id":13,"surface":"following","left":67,"lemma":"follow","right":76,"POS":"VBG","NER":"O","Speaker":"PER0"},"14":{"id":14,"surface":"``","left":77,"lemma":"``","right":79,"POS":"``","NER":"O"},"15":{"id":15,"surface":"Real","left":79,"lemma":"real","right":83,"POS":"JJ","NER":"O","Speaker":"PER1"},"16":{"id":16,"surface":"life","left":84,"lemma":"life","right":88,"POS":"NN","NER":"O","Speaker":"PER1"},"17":{"id":17,"surface":"dilemmas","left":89,"lemma":"dilemma","right":97,"POS":"NNS","NER":"O","Speaker":"PER1"},"18":{"id":18,"surface":"often","left":98,"lemma":"often","right":103,"POS":"RB","NER":"O","Speaker":"PER1"},"19":{"id":19,"surface":"present","left":104,"lemma":"present","right":111,"POS":"JJ","NER":"DATE","Speaker":"PER1"},"20":{"id":20,"surface":"choices","left":112,"lemma":"choice","right":119,"POS":"NNS","NER":"O","Speaker":"PER1"},"21":{"id":21,"surface":"between","left":120,"lemma":"between","right":127,"POS":"IN","NER":"O","Speaker":"PER1"},"22":{"id":22,"surface":"equally","left":128,"lemma":"equally","right":135,"POS":"RB","NER":"O","Speaker":"PER1"},"23":{"id":23,"surface":"unfavorable","left":136,"lemma":"unfavorable","right":147,"POS":"JJ","NER":"O","Speaker":"PER1"},"24":{"id":24,"surface":"or","left":148,"lemma":"or","right":150,"POS":"CC","NER":"O","Speaker":"PER1"},"25":{"id":25,"surface":"disagreeable","left":151,"lemma":"disagreeable","right":163,"POS":"JJ","NER":"O","Speaker":"PER1"},"26":{"id":26,"surface":"alternatives","left":164,"lemma":"alternative","right":176,"POS":"NNS","NER":"O","Speaker":"PER1"},"27":{"id":27,"surface":".","left":176,"lemma":".","right":177,"POS":".","NER":"O","Speaker":"PER1"}},["ROOT",["S",["ADVP",["RB",1],["RB",2]],["NP",["PRP",3]],["VP",["VBD",4],["NP",["PRP",5]],["ADVP",["RB",6],["VP",["VBG",7],["S",["VP",["TO",8],["VP",["VB",9],["S",["NP",["NNP",10]],["NP",["NP",["NN",11],["DT",12]],["PP",["VBG",13],["NP",["`",14],["NP",["JJ",15],["NN",16],["NNS",17]],["ADVP",["RB",18],["NP",["JJ",19],["NNS",20]]],["PP",["IN",21],["NP",["NP",["RB",22],["JJ",23]],["CC",24],["NP",["JJ",25],["NNS",26]]]]]]]]]]]]]],[".",27]]],[{"name":"root","governor":0,"dependent":4},{"name":"advmod","governor":2,"dependent":1},{"name":"advmod","governor":4,"dependent":2},{"name":"nsubj","governor":4,"dependent":3},{"name":"dobj","governor":4,"dependent":5},{"name":"advmod","governor":4,"dependent":6},{"name":"dep","governor":6,"dependent":7},{"name":"mark","governor":9,"dependent":8},{"name":"xcomp","governor":7,"dependent":9},{"name":"nsubj","governor":11,"dependent":10},{"name":"xcomp","governor":9,"dependent":11},{"name":"dep","governor":11,"dependent":12},{"name":"case","governor":17,"dependent":13},{"name":"punct","governor":17,"dependent":14},{"name":"amod","governor":17,"dependent":15},{"name":"compound","governor":17,"dependent":16},{"name":"nmod","governor":11,"dependent":17},{"name":"advmod","governor":17,"dependent":18},{"name":"amod","governor":20,"dependent":19},{"name":"nmod:npmod","governor":18,"dependent":20},{"name":"case","governor":23,"dependent":21},{"name":"advmod","governor":23,"dependent":22},{"name":"nmod","governor":17,"dependent":23},{"name":"cc","governor":23,"dependent":24},{"name":"amod","governor":26,"dependent":25},{"name":"conj","governor":23,"dependent":26},{"name":"punct","governor":4,"dependent":27}]]"""

  val parsedSentence:ParsedSentence = ( SortedMap( 0 -> Node(0,"ROOT",None,None,None,None,None,None), 1 -> Node(1,"RB",Some("Oddly"),Some("oddly"),Some("O"),Some("PER0"),Some(0),Some(5)), 2 -> Node(2,"RB",Some("enough"),Some("enough"),Some("O"),Some("PER0"),Some(6),Some(12)), 3 -> Node(3,"PRP",Some("I"),Some("I"),Some("O"),Some("PER0"),Some(13),Some(14)), 4 -> Node(4,"VBD",Some("found"),Some("find"),Some("O"),Some("PER0"),Some(15),Some(20)), 5 -> Node(5,"PRP",Some("it"),Some("it"),Some("O"),Some("PER0"),Some(21),Some(23)), 6 -> Node(6,"RB",Some("quite"),Some("quite"),Some("O"),Some("PER0"),Some(24),Some(29)), 7 -> Node(7,"VBG",Some("empowering"),Some("empower"),Some("O"),Some("PER0"),Some(30),Some(40)), 8 -> Node(8,"TO",Some("to"),Some("to"),Some("O"),Some("PER0"),Some(41),Some(43)), 9 -> Node(9,"VB",Some("hear"),Some("hear"),Some("O"),Some("PER0"),Some(44),Some(48)), 10 -> Node(10,"NNP",Some("Natalia"),Some("Natalia"),Some("PERSON"),Some("PER0"),Some(49),Some(56)), 11 -> Node(11,"NN",Some("state"),Some("state"),Some("O"),Some("PER0"),Some(57),Some(62)), 12 -> Node(12,"DT",Some("the"),Some("the"),Some("O"),Some("PER0"),Some(63),Some(66)), 13 -> Node(13,"VBG",Some("following"),Some("follow"),Some("O"),Some("PER0"),Some(67),Some(76)), 14 -> Node(14,"``",Some("``"),Some("``"),Some("O"),None,Some(77),Some(79)), 15 -> Node(15,"JJ",Some("Real"),Some("real"),Some("O"),Some("PER1"),Some(79),Some(83)), 16 -> Node(16,"NN",Some("life"),Some("life"),Some("O"),Some("PER1"),Some(84),Some(88)), 17 -> Node(17,"NNS",Some("dilemmas"),Some("dilemma"),Some("O"),Some("PER1"),Some(89),Some(97)), 18 -> Node(18,"RB",Some("often"),Some("often"),Some("O"),Some("PER1"),Some(98),Some(103)), 19 -> Node(19,"JJ",Some("present"),Some("present"),Some("DATE"),Some("PER1"),Some(104),Some(111)), 20 -> Node(20,"NNS",Some("choices"),Some("choice"),Some("O"),Some("PER1"),Some(112),Some(119)), 21 -> Node(21,"IN",Some("between"),Some("between"),Some("O"),Some("PER1"),Some(120),Some(127)), 22 -> Node(22,"RB",Some("equally"),Some("equally"),Some("O"),Some("PER1"),Some(128),Some(135)), 23 -> Node(23,"JJ",Some("unfavorable"),Some("unfavorable"),Some("O"),Some("PER1"),Some(136),Some(147)), 24 -> Node(24,"CC",Some("or"),Some("or"),Some("O"),Some("PER1"),Some(148),Some(150)), 25 -> Node(25,"JJ",Some("disagreeable"),Some("disagreeable"),Some("O"),Some("PER1"),Some(151),Some(163)), 26 -> Node(26,"NNS",Some("alternatives"),Some("alternative"),Some("O"),Some("PER1"),Some(164),Some(176)), 27 -> Node(27,".",Some("."),Some("."),Some("O"),Some("PER1"),Some(176),Some(177))),  List("ROOT", List("S", List("ADVP", List("RB",1), List("RB",2)), List("NP", List("PRP",3)), List("VP", List("VBD",4), List("NP", List("PRP",5)), List("ADVP", List("RB",6), List("VP", List("VBG",7), List("S", List("VP", List("TO",8), List("VP", List("VB",9), List("S", List("NP", List("NNP", 10)), List("NP", List("NP", List("NN", 11), List("DT", 12)), List("PP", List("VBG", 13), List("NP", List("`", 14), List("NP", List("JJ", 15), List("NN", 16), List("NNS", 17)), List("ADVP", List("RB", 18), List("NP", List("JJ", 19), List("NNS", 20))), List("PP", List("IN", 21), List("NP", List("NP", List("RB", 22), List("JJ", 23)), List("CC", 24), List("NP", List("JJ", 25), List("NNS", 26)))))))))))))), List(".", 27))), List(Dependency("root",0,4), Dependency("advmod",2,1), Dependency("advmod",4,2), Dependency("nsubj",4,3), Dependency("dobj",4,5), Dependency("advmod",4,6), Dependency("dep",6,7), Dependency("mark",9,8), Dependency("xcomp",7,9), Dependency("nsubj",11,10), Dependency("xcomp",9,11), Dependency("dep",11,12), Dependency("case",17,13), Dependency("punct",17,14), Dependency("amod",17,15), Dependency("compound",17,16), Dependency("nmod",11,17), Dependency("advmod",17,18), Dependency("amod",20,19), Dependency("nmod:npmod",18,20), Dependency("case",23,21), Dependency("advmod",23,22), Dependency("nmod",17,23), Dependency("cc",23,24), Dependency("amod",26,25), Dependency("conj",23,26), Dependency("punct",4,27)))
}