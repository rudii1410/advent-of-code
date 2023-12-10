extends SceneTree

var util = preload("res://src/util.gd").new()

func _init():
	var inputs = parse_input(util.read_input("day_09.txt"))
	#print(pascal_row(1))
	print(part_one(inputs))
	print(part_two(inputs))

func part_one(data: Array) -> int:
	var result = 0
	for d in data:
		result += solution(d)
	return result

func part_two(data: Array) -> int:
	var result = 0
	for d in data:
		d.reverse()
		result += solution(d)
	return result

func solution(data: Array) -> int:
	var result = 0
	var size = data.size()
	var pascal = pascal_triangle(size)
	var tmp_result = 0
	for p in range(1, pascal.size()):
		var min = 1
		if p % 2 == 0:
			min = -1
		result += (min * pascal[p] * data[size - p])
	return result

func pascal_triangle(row: int) -> Array:
	var result = [1]
	for i in range(1, row + 1):
		result.append(result[i - 1] * (row - i + 1) / i)
	return result

func parse_input(inputs: Array) -> Array:
	var result = []
	for line in inputs:
		result.append(Array(line.split(" ")).map(func(a): return a.to_int()))
	return result
