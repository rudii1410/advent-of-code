extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var parsed_input = parse_input(util.read_input("day_05.txt"))
	var seeds = parse_seeds(parsed_input)
	var plant_map = parse_plant_map(parsed_input)
	# part_one(seeds, plant_map)
	part_two(seeds, plant_map)

func part_one(seeds: Array, plant_map: Array):
	var result = (1 << 63) - 1
	for s in seeds:
		result = min(result, solution_3({ "start": s, "len": 1 }, plant_map))
	print("result 1: %d" % result)

func part_two(seeds: Array, plant_map: Array):
	var i = 0
	var ranges = []
	var result = (1 << 63) - 1
	while i < seeds.size():
		print("start %d - %d" % [seeds[i], seeds[i + 1]])
		result = min(result, solution_4({ "start": seeds[i], "len": seeds[i + 1] }, plant_map))
		print("end")
		i += 2
	
	print("result 2: %d" % result)

func parse_input(inputs: Array) -> Array:
	var new_line = 0
	var result = []
	for x in range(inputs.size()):
		if (x == inputs.size() - 1):
			result.append(inputs.slice(new_line, x + 1))
		elif (inputs[x] == ""):
			result.append(inputs.slice(new_line, x))
			new_line = x + 1

	return result

func parse_seeds(raw_plant_map: Array) -> Array:
	return Array(raw_plant_map[0][0].split(": ")[1].split(" ")).map(func(s): return s.to_int())

func parse_plant_map(raw_plant_map: Array) -> Array:
	var result = []
	var i = 1
	while i < raw_plant_map.size():
		var mapped = []
		for map in raw_plant_map[i].slice(1, raw_plant_map[i].size()):
			var config = Array(map.split(" ")).map(func(s): return s.to_int())
			mapped.append([[config[1], config[1] + config[2] - 1], [config[0], config[0] + config[2] - 1]])
		mapped.reverse()
		result.append(mapped)
		i += 1

	return result

var memo = {}
func solution(seeds: Array, plant_map: Array) -> int:
	var min = (1 << 63) - 1
	for seed in seeds:
		var start = seed
		for map in plant_map:
			for m in map:
				if in_between(start, m[0]):
					var pair = m[1][0] + (start - m[0][0])
					start = pair
					break
		min = min(min, start)
	
	return min

func solution_3(seed_range: Dictionary, plant_map: Array) -> int:
	var start = seed_range["start"]
	var end = start + seed_range["len"]
	var result = (1 << 63) - 1
	while start < end:
		result = min(result, dsa(0, start, plant_map))
		start += 1
	return result

func solution_2(seed_range: Dictionary, plant_map: Array) -> int:
	var result = (1 << 63) - 1
	var i = seed_range["start"]
	var end = i + seed_range["len"]
	while i < end:
		result = min(result, asd(i, plant_map))
		i += 1
	return result

func solution_4(seed_range: Dictionary, plant_map: Array) -> int:
	var a = []
	a.resize(seed_range["len"])
	print(a.size())
	# print(range(seed_range["start"], seed_range["len"]))
	return 0

var memo1 = {}
func dsa(level: int, seed: int, plant_map: Array) -> int:
	if (level >= plant_map.size()):
		return seed
	if (memo1.has(level) && memo1[level].has(seed)):
		print("use memo %d %d" % [level, seed])
		return memo1[level][seed]

	var pos = search_pos(seed, plant_map[level])
	var result = dsa(level + 1, pos, plant_map)
	if not memo1.has(level):
		memo1[level] = {}
	memo1[level][seed] = result
	return result

func search_pos(seed: int, map: Array) -> int:
	for m in map:
		if in_between(seed, m[0]):
			var pair = m[1][0] + (seed - m[0][0])
			return pair
	return seed

func asd(seed: int, plant_map: Array) -> int:
	var start = seed
	var min = (1 << 63) - 1
	for map in plant_map:
		for m in map:
			if in_between(start, m[0]):
				var pair = m[1][0] + (start - m[0][0])
				start = pair
				break
	min = min(min, start)
	
	return min

func in_between(num: int, range: Array) -> bool:
	return num >= range[0] and num <= range[1]

func is_range_intersect(left: Array, right: Array) -> bool:
	return in_between(left[0], right) or in_between(left[1], right)
