package models.com.bulba

trait Populator {
    def populate() : Canvas[Seq[Cell], Seq[Seq[Cell]]]
}

class RandomPopulator extends Populator {
  def populate(): Canvas[Seq[Cell], Seq[Seq[Cell]]] = ???
}
