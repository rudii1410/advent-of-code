extends SceneTree

var util = preload("res://src/util.gd").new()
var symbol_strength = {
	"2": 1, "3": 2, "4": 3, "5": 4,
	"6": 5, "7": 6, "8": 7, "9": 8,
	"T": 9, "J": 10, "Q": 11, "K": 12,
	"A": 13
}
var deck_strength = {
	"OTHER": 0,
	"HIGH_CARD": 1,
	"ONE_PAIR": 2,
	"TWO_PAIR": 3,
	"THREE_OF_A_KIND": 4,
	"FULL_HOUSE": 5,
	"FOUR_OF_A_KIND": 6,
	"FIVE_OF_A_KIND": 7,
}
var deck_pattern = {
	"5": "FIVE_OF_A_KIND",
	"14": "FOUR_OF_A_KIND",
	"23": "FULL_HOUSE",
	"113": "THREE_OF_A_KIND",
	"122": "TWO_PAIR",
	"1112": "ONE_PAIR"
}

func _init():
	var inputs = parse_input(util.read_input("day_07.txt"))
	print("Part one: %s" % part_one(inputs))
	print("Part two: %s" % part_two(inputs))

func part_one(input: Array) -> int:
	var deck_info = []
	for i in input:
		i["pattern"] = map_card_pattern(get_cards_pattern(i["card_group"]))
		deck_info.append(i)

	return calculate_result(deck_info)

func part_two(input: Array) -> int:
	symbol_strength["J"] = 0

	var deck_info = []
	for i in input:
		var pattern = []
		if i["card"] == "JJJJJ":
			pattern = [5]
		else:
			var tmp_card_group = i["card_group"].duplicate()
			tmp_card_group.erase("J")
			pattern = get_cards_pattern(tmp_card_group)
			if i["card_group"].has("J") && pattern.size() < 5:
				var last = pattern.size() - 1
				pattern[last] = pattern[last] + i["card_group"]["J"]

		i["pattern"] = map_card_pattern(pattern)
		deck_info.append(i)

	return calculate_result(deck_info)

func get_cards_pattern(card_group: Dictionary) -> Array:
	var pattern = []
	var max_score = 0
	var max_symbol = ""
	for c in card_group:
		pattern.append(card_group[c])
		var tmp = (card_group[c] * 100) + symbol_strength[c]
		if tmp > max_score:
			max_score = tmp
			max_symbol = c

	pattern.sort()
	return pattern

func calculate_result(deck_info: Array) -> int:
	deck_info.sort_custom(deck_sorter)

	var result = 0
	for i in range(deck_info.size()):
		result += ((i + 1) * deck_info[i]["score"])
	
	return result

func map_card_pattern(pattern: Array) -> String:
	var pattern_str = util.array_to_string(pattern)
	if deck_pattern.has(pattern_str):
		return deck_pattern[pattern_str]
	else:
		return "HIGH_CARD"


# Input parser

func parse_input(raw_inputs: Array) -> Array:
	var result = []
	for line in raw_inputs:
		var card_info = line.split(" ")
		var card_array = []
		var card_group = {}

		for c in card_info[0]:
			card_array.append(c)
			if card_group.has(c):
				card_group[c] = card_group[c] + 1
			else:
				card_group[c] = 1

		card_array.sort_custom(
			func(a, b): return symbol_strength[a] > symbol_strength[b]
		)

		result.append({ 
			"original_card": card_info[0],
			"card": util.array_to_string(card_array),
			"card_group": card_group,
			"score": card_info[1].to_int()
		})
	return result


# Custom sorter

func card_value_by_arrangement(card) -> int:
	var result = 0
	var multiplier = 100_000_000
	for c in card["original_card"]:
		result += (symbol_strength[c] + 1) * multiplier
		multiplier /= 100
	return result

func deck_sorter(a, b) -> bool:
	var score_a = deck_strength[a["pattern"]]
	var score_b = deck_strength[b["pattern"]]
	if (score_a == score_b):
		score_a = card_value_by_arrangement(a)
		score_b = card_value_by_arrangement(b)
	return score_a < score_b
