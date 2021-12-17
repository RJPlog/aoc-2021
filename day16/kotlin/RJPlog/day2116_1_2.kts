import java.io.File

fun packDecoder(input1: String, puzzle_part: Int): Long {
	var version: Int
	var typID: Int
	var versionSum: Long = 0
	var opPacket = input1
	var litValResult: Long = 0
	var operator: String = ""
	var numberSubPackets: Int = 0
	var condSubPack1: Boolean = false
	var litValList = mutableListOf<Long>()

	var i: Int = 0
	while (opPacket.length > 7) {  //opPacket.length >5
		i += 1
		version = opPacket.take(3).toInt(2)
		versionSum = versionSum + version
		opPacket = opPacket.drop(3)
		typID = opPacket.take(3).toInt(2)

		when (typID) {
			(0) -> operator = "sum"
			(1) -> operator = "prod"
			(2) -> operator = "min"
			(4) -> operator = "data"
			(3) -> operator = "max"
			(5) -> operator = "greater"
			(6) -> operator = "less"
			(7) -> operator = "equal"
		}
		if (typID != 4) {
		println("$operator")
		}

		opPacket = opPacket.drop(3)

		if (typID == 4) {
			var parseEnd = false
			var litVal: String = ""
			while (!parseEnd) {
				litVal = litVal + opPacket.take(5).drop(1)
				// when operator
				if (opPacket[0] == '0') {
					parseEnd = true
				}
				opPacket = opPacket.drop(5)
			}

			litValList.add(litVal.toLong(2))
			println("     ${litVal.toLong(2)}")
			//println("   $litValList")
		} else {
			if (opPacket[0] == '0') {
				opPacket = opPacket.drop(1)
				// next 15 show total length of bit of the subpacket
				val subPacketLength = opPacket.take(15).toInt(2)

				opPacket = opPacket.drop(15)
				if (puzzle_part == 1) {
//					println("start Subpackage 0")
					println("(")
					versionSum = versionSum + packDecoder(opPacket.take(subPacketLength), puzzle_part)
//					println("stop Subpackage 0")
					println(")")
				} else {
					// when (operator)...

				}
				opPacket = opPacket.drop(subPacketLength)
			} else if (opPacket[0] == '1') {
				condSubPack1 = true
				opPacket = opPacket.drop(1)
				numberSubPackets = opPacket.take(11).toInt(2)
//				println("start subPack 1: ${opPacket.take(11).toInt(2)}")
				println("[")
				opPacket = opPacket.drop(11)
			}

		}
//		println("numberSubPackets: $numberSubPackets")
		if (condSubPack1) {
		if (numberSubPackets == 0) {
//			println("stop subPack 1")
			println("]")
			condSubPack1 = false
			litValList.clear()
		}
				numberSubPackets -= 1
		}
	}

	if (puzzle_part == 1) {
		return versionSum
	} else {
		return litValResult
	}
}

fun main(args: Array<String>) {
	var opPacket: String = ""

	File("day2116_puzzle_input.txt").forEachLine {
		it.forEach {
			opPacket = opPacket + it.toString().toInt(16).toString(2).padStart(4, '0')
		}
	}
	println("start")
	println()
	var solution1 = packDecoder(opPacket, 1)
	println()

//	var solution2 = packDecoder(opPacket, 2)


	println()

	// tag::output[]
// print solution for part 1
	println("*******************************")
	println("--- Day 16: Packet Decoder  ---")
	println("*******************************")
	println("Solution for part1")
	println("   $solution1 ")
	println()
// print solution for part 2
	println("*******************************")
	println("Solution for part2")
//	println("   $solution2 ")
	println()
// end::output[]
}