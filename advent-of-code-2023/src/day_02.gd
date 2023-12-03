extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = util.read_input("day_02.txt")
	var parsed_inputs = inputs.map(parse_input)
	part_two(parsed_inputs)
	quit()
	
func part_one(data: Array):
	var expected = { "blue": 14, "green": 13, "red": 12 }
	var result = 0
	for d in data:
		for r in d["rounds"]:
			if (not check_cube(r, expected)):
				result -= d["game"]
				break
		
		result += d["game"]
	
	print(result)

func part_two(data: Array):
	var result = 0
	for d in data:
		var tmp_cubes = { "blue": 0, "green": 0, "red": 0 }
		for r in d["rounds"]:
			for key in r:
				tmp_cubes[key] = max(tmp_cubes[key], r[key])
		
		var tmp_result = 1
		for key in tmp_cubes:
			tmp_result *= tmp_cubes[key]
		if (tmp_result > 1):
			result += tmp_result
	print(result)

func parse_input(line: String):
	var split_line = line.split(":")
	var num_game = split_line[0].split(" ")[1].to_int()
	var rounds = Array(split_line[1].split(";")).map(func(r): return parse_cube(r.split(",")))
	return { "game": num_game, "rounds": rounds }

func parse_cube(line: PackedStringArray) -> Dictionary:
	var result = {}
	var mapped_line = Array(line).map(func(str): return str.trim_prefix(" "))
	for s in mapped_line:
		var split = s.split(" ")
		result[split[1]] = split[0].to_int()
	return result

func check_cube(data: Dictionary, expect: Dictionary) -> bool:
	for key in data:
		if (data[key] > expect[key]):
			return false
	
	return true
