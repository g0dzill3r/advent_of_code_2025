package day12

data class Puzzle (
    val pieces: List<Piece>,
    val regions: List<Region>
) {
    companion object {
        fun parse (input: String): Puzzle {
            val els = input.split ("\n\n")
            val pieces = els.subList(0, els.size - 1).map { it.split ("\n") }
            val regions = els.subList (els.size - 1, els.size)[0].split ("\n")
            return Puzzle (
                pieces.map { Piece.parse (it) },
                regions.map { Region.parse (it) }
            )
        }
    }
}
