extends SceneTree

var util = preload("res://src/util.gd").new()

var dict = {
	"one": "1", "two": "2", "three": "3",
	"four": "4", "five": "5", "six": "6",
	"seven": "7", "eight": "8", "nine": "9"
}
var reversed_dict = {
	"eno": "1", "owt": "2", "eerht": "3",
	"ruof": "4", "evif": "5", "xis": "6",
	"neves": "7", "thgie": "8", "enin": "9",
}

func _init():
	var inputs = util.read_input("input.txt")
	var regex = RegEx.new()
	var result = 0
	var normal_pattern = "\\d|one|two|three|four|five|six|seven|eight|nine"
	var reversed_pattern = "\\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin"
	for item in inputs:
		regex.compile(normal_pattern)
		var first = convert_word_to_num(regex.search(item).get_string())
		regex.compile(reversed_pattern)
		var last = convert_word_to_num(regex.search(reverse(item)).get_string(), true)
		var num = "%s%s" % [first, last]
		result += num.to_int()
	
	print(result)
	quit()

func convert_word_to_num(str: String, reversed: bool = false) -> String:
	if not reversed and dict.has(str):
		return dict[str]
	elif reversed and reversed_dict.has(str):
		return reversed_dict[str]
	else:
		return str

func reverse(arg: String) -> String:
	var arr := arg.to_utf32_buffer().to_int32_array()
	arr.reverse()
	return arr.to_byte_array().get_string_from_utf32()
