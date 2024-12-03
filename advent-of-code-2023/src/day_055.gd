extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var parsed_input = parse_input(util.read_input("day_05.txt"))
	var seeds = parse_seeds(parsed_input)
	var plant_map = parse_plant_map(parsed_input)

	print(part_two(seeds, plant_map))

func part_two(seeds: Array, plant_map: Array) -> int:
	var i = 0
	var ranges = []
	var result = (1 << 63) - 1
	var partition_size = 10_000_000
	while i < seeds.size():
		var j = 0
		var start = seeds[i]
		var len = seeds[i + 1]
		print("start %d - %d" % [start, len])
		while len > 0:
			print("new partition, remaining len: %d" % len)
			j = partition_size
			if len - j < 0:
				j = len
			len -= j
			
			solution(start, j, plant_map)
		# result = min(result, solution_4({ "start": seeds[i], "len": seeds[i + 1] }, plant_map))
		print("end")
		i += 2
	
	print("real end")
	return result

func solution(start_pos: int, len: int, plant_map: Array) -> int:
	var res = {}
	var i = start_pos
	while i < start_pos + len:
		res[i] = 0
		i += 1
		
	return 0

# Parser
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
