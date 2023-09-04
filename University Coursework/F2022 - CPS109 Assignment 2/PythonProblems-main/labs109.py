# problem 8 - count dominators
def count_dominators(items):
    if items == []:
        return 0
    
    dominators = 0
    largest = float("-inf")

    reversed = items[::-1]

    for i in range(len(reversed)):
        if largest < reversed[i]:
            dominators += 1
            largest = reversed[i]
    
    return dominators


# problem 11 - taxi zoom zoom
def taxi_zum_zum(moves):
    current_location = [0,0]
    orientation = [0, 1]

    for char in moves:
        if char == "R":
            if orientation == [0, 1]:
                orientation = [1, 0]
            elif orientation == [1, 0]:
                orientation = [0, -1]
            elif orientation == [0, -1]:
                orientation = [-1, 0]
            elif orientation == [-1, 0]:
                orientation = [0, 1]
        elif char == "L":
            if orientation == [0, 1]:
                orientation = [-1, 0]
            elif orientation == [-1, 0]:
                orientation = [0, -1]
            elif orientation == [0, -1]:
                orientation = [1, 0]
            elif orientation == [1, 0]:
                orientation = [0, 1]
        elif char == "F":
            current_location[0] += orientation[0]
            current_location[1] += orientation[1]

    return tuple(current_location)

# problem 13 - rooks on a rampage
def safe_squares_rooks(n, rooks):
    board = [ [True for i in range(n)] for j in range(n)]
    safe = 0

    for x,y in rooks:
        for i in range(n):
            for j in range(n):
                if (x == i or y == j):
                    board[i][j] = False
                    
    for row in board:
        for square in row:
            if square:
                safe += 1
    
    return safe

# problem 52 - bishops in a binge
def safe_squares_bishops(n, bishops):
    board = [ [True for i in range(n)] for j in range(n)]
    safe = 0

    for x,y in bishops:
        for i in range(n):
            for j in range(n):
                if (x,y) == (i, j):
                    board[i][j] = False
                if abs(x-i) == abs(y-j):
                    board[i][j] = False
                    
    for row in board:
        for square in row:
            if square:
                safe += 1
    
    return safe

# problem 27 - best one out of three
def tukeys_ninthers(items):
    if len(items) == 1:
        return items[0]
    if len(items) == 3:
        if (items[0] > items[1] and items[0] < items[2]) or (items[0] > items[2] and items[0] < items[1]):
            return items[0]
        elif ((items[1] > items[0] and items[1] < items[2]) or (items[1] > items[2] and items[1] < items[0])):
            return items[1]
        else:
            return items[2]
    else:
        one_third = items[0:len(items)//3]
        two_third = items[len(items)//3:2*(len(items)//3)]
        three_third = items[2*(len(items)//3):len(items)]

        return tukeys_ninthers([tukeys_ninthers(one_third), tukeys_ninthers(two_third), tukeys_ninthers(three_third)])

# problem 33 - sum of two squares
def sum_of_two_squares(n):
    from_above = int(n**(1/2))
    from_below = 1
    while (from_below <= from_above):
        if from_above**2 + from_below**2 == n:
            return (from_above, from_below)
        else:
            if (from_above)**2 + (from_below)**2 > n:
                from_above -= 1
            else:
                from_below += 1

    return None

# problem 36 - expand positive intervals
def expand_intervals(intervals):
    if intervals == '':
        return []

    expanded = intervals.split(",")
    better_expanded = []


    for interval in expanded:
        if "-" in interval:
            interval = list(range(int(interval.split("-")[0]), int(interval.split("-")[1]) + 1))
        else:
            interval = int(interval)
        better_expanded.append(interval)
    
    even_better_expanded = []

    for elem in better_expanded:
        if type(elem) == int:
            even_better_expanded.append(elem)
        else:
            even_better_expanded += elem
    return even_better_expanded

# problem 51 - count and say
def count_and_say(digits):
    result = ""
    last_char = digits[0]
    count = 0

    if len(digits) == 1:
        return "1" + digits

    for char in digits:
        if char == last_char:
            count += 1
        else:
            result += str(str(count) + str(last_char))
            count = 1
        last_char = char
    
    result += str(str(count) + str(last_char))

    return result

# problem 55 - reverse the vowels
def reverse_vowels(text):
    vowels = []
    new = ""
    for char in text.lower():
        if char in "aeiou":
            vowels.append(char)
    
    vowel_index = len(vowels) - 1

    for char in text:
        if char in "aeiou":
            new += vowels[vowel_index]
            vowel_index -= 1
        elif char in "AEIOU":
            new += vowels[vowel_index].upper()
            vowel_index -= 1
        else:
            new += char

    return new

# problem 66 - reversed collatz sequences
def ztalloc(shape):
    result = 1
    lastChar = ""
    for char in shape[::-1]:
        if char == "d":
            result *= 2
        if char == "u": # is this the right solution? seems lazy to me...
            if lastChar == "u": # you can never go up twice (3n+1 will always equal even if n is odd)
                return None
            if (result - 1) % 3 == 0: # otherwise if divisible by 3 when subtracted by 1, do it
                result = (result - 1) // 3
            else:
                return None
        lastChar = char
    return result