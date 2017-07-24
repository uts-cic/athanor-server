package au.edu.utscic.athanorserver

/**
  * Created by andrew@andrewresearch.net on 19/7/17.
  */


  import org.scalatest._

  abstract class UnitSpec extends FlatSpec with Matchers with
    OptionValues with Inside with Inspectors

