package models.com.bulba

trait Populator {
    def populate() : Canvas[Cell]
}

class RandomPopulator extends Populator {
  def populate(): Canvas[Cell] = ???
}
