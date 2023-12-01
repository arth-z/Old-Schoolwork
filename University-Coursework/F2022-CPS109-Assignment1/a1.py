"""
A program for a very simple quadratic formula solver.
Essentially does the quadratic formula for you, given a quadratic equation entered in standard form (ax^2 + bx + c), where a,b,c MUST be single-digit integer values.

Again, ONLY WORKS WHEN A, B, AND C ARE INTEGERS WITH ABSOLUTE VALUE LESS THAN 10 (aka single digit values)!
While this does somewhat limit its usability, it's technically less work (albeit not much) than having to find a, b, and c yourself for these sorts of equations, 
and alot of gr.10 worksheets have a good chunk of their quadratic formula questions only have integer a, b, and c values less than 10.

Unfortunately couldn't figure out a way in time to account for n-digit integer coefficients, nor the added problem of decimal coefficients with n-significant figures.

The trick with this program is that it doesn't care what order you enter the variables in, and it doesn't care if you don't indicate the explicit sign value of each term.
Nor does the program care that you don't enter an explicit value of b or c - a is the only one that matters (you need to have a variable squared in your function for it to count)
The only thing that can screw it up are values of a,b,c with more than one digit, as well as deviations from standard form (like brackets, unsimplified values etc.)
"""

from math import sqrt # import math so we can use sqrt (also without having to do math.sqrt)

# outputs a tuple containing the two possible answers to the quadratic formula (plus and minus variations) at indices 0 and 1 respectively
# if the discriminant is negative it outputs "Nonreal root" and "Nonreal root" respectively
def quadraticFormula(a_int, b_int, c_int):
    
    # explicitly cast inputs as floats to keep types consistent and prevent truncation of digits
    a = float(a_int)
    b = float(b_int)
    c = float(c_int)

    nonreal_solution = b**2 - 4*a*c < 0 # boolean variable representing negative discriminant (negative sqrt = nonreal roots)

    if nonreal_solution == True: # if negative discriminant
        return ("Nonreal root", "Nonreal root") # return nonreal roots
    else: # else just return the quadratic formula, both plus minus variations in one tuple
          # by the way, -0.0 is a possible output for some reason
        return  ( (-b + sqrt(b*b - 4*a*c))/(2*a), (-b - sqrt(b*b - 4*a*c))/(2*a) )

# function to grab the a, b, and c values from a string that's supposed to be a quadratic function
# assumes that the input string has no brackets, and contains only lowercase letters and the "^" symbol
# outputs a tuple (a,b,c) for each respective variable in the quadratic formula
def getABC(eqn_spaces):

    alphabet = "abcdefghijklmnopqrstuvwxyz" #alphabet string to check if a character is a letter (isalpha() wasn't working well for me)

    eqn = "" # define an empty string first

    # strips away all spaces in the input string, eqn_spaces, by adding only nonspace characters to eqn
    for char in eqn_spaces:
        if char != " ":
            eqn += char

    # define variables first before working with them, good form(?)
    # also lets us establish a default value for a, b, and c if we can't actually catch them in our function
    a = 0
    b = 0
    c = 0

    # iteration over space-stripped quadratic equation to grab a, b, and c variables
    # much of the if-conditions in this loop can be grasped intuitively if you just kind of look at a quadratic function and see how it's written
    # also we use i in range because I need access to our current index in the string
    
    for i in range(len(eqn)):
        if eqn[i] in alphabet: # check if the character we are on is a letter
                               # means that it can either be a or b

            if i != len(eqn) - 1 and eqn[i+1] == "^": # first condition guards against out-of-range error, second condition checks if next char is "^" symbol
                if i == 0: # means that the char we are on has a "^" in front of it and nothing behind it, meaning it is "x^2" in standard form, meaning a = 1
                    a = 1
                elif eqn[i-1] == "-": # means that the char we are on has a "^" in front of it and a "-" behind it, meaning implicit -1 in -x^2, meaning a = -1
                    a = -1
                elif eqn[i-1].isdigit(): # checks for an integer coefficient
                    if i > 0 and eqn[i-2] == "-": # check behind the integer coefficient for negative sign, guard against index out of range error
                        a = -1 * int(eqn[i-1])
                    else: # else, a = the integer coefficient since it's before "x^"
                        a = int(eqn[i-1])
                else:
                    a = 1
            else: # if there isn't a ^ after the letter
                if i == 0: # basically means we have a letter with nothing behind or in front of it, meaning b = 1 
                    b = 1 
                elif eqn[i-1] == "-": # basically means we have a letter with a negative sign behind it and no "^" in front, meaning b = -1
                    b = -1
                elif eqn[i-1].isdigit(): # integer coefficient behind the letter
                    if i > 0 and eqn[i-2] == "-": # guard against index out of range, check behind the integer coefficient for a negative sign
                        b = -1 * int(eqn[i-1])
                    else: # if there isn't a negative sign, then b = integer coefficient
                        b = int(eqn[i-1])
                else: # this basically means that we have a single letter with no "^" in front and no integer or "-" behind, meaning b = 1
                    b = 1
        elif eqn[i].isdigit(): # check if the character we are on is a number 
                               # (case to try to catch the c-value)

            if i == len(eqn) - 1: # if last character (nothing in front, so no variable in front, meaning c in ax^2 + bx + c)
                if eqn[i-1] == "-": # if negative sign behind
                    c = -1*int(eqn[i]) # c is equal to current character cast as int times -1
                elif eqn[i-1] != "^": # guard against the case where somebody enters ax^2 as the last term in their function, meaning c would be equal to 2 when it's not
                    c = int(eqn[i])
            elif i == 0: # if first character (nothing behind)
                if eqn[i+1] not in alphabet: # if the next character is NOT a letter, that means that the char we're on is C
                    c = int(eqn[i])
            elif eqn[i+1] not in alphabet: # otherwise, if we find a number with a non-letter value in front of it
                if eqn[i-1] == "-": # check for character behind to see if negative sign and make sure c is the right sign value
                    c = -1*int(eqn[i]) 
                elif eqn[i-1] != "^": # guard against the case in which we find "x^2", because we don't want to accidentally flag the exponent as c since it fulfills the "integer with no letter after it" condition
                    c = int(eqn[i])

    print("Terms in the quadratic formula: " + "a = " + str(a) + ", b = " + str(b) + ", c = " + str(c)) # print out terms in the quadratic formula
    return (a, b, c) # return tuple (a,b,c)

if __name__ == "__main__":
    variable_name = "" # define variable before we start working with - we do this to make sure our output correctly assigns values to the variables given us
                       # as in, it's not always "x", we could be solving for "v" or "w"

    print("Note: this only works for quadratic functions with a, b, and c values that are integers whose absolute values are less than 10.") # disclaimer about usability of program
    eqn = input("Enter a quadratic expression in standard form (ax^2+by+c, lowercase variables only, avoid brackets): ") # accept user input

    alphabet = "abcdefghijklmnopqrstuvwxyz" # alphabet string for checking if something is a letter

    # making sure we get the right variable name
    for char in eqn:
        if char in alphabet: # assume the only letters in our input string is going to be the x-variable we're trying to solve for...
            variable_name = char # and assign that single only letter as our variable name

    a, b, c = getABC(eqn) # assign a, b, and c values in this namespace to the a, b, and c values returned by the function, pair-to-pair

    if (a == 0): # if a is 0, it's not a quadratic function (and also calling quadraticFormula on it would lead to a divbyzero error)
        print ("Not a quadratic function!")
    else: # then just print out what the possible roots of the function are using the variable name given
        print("Roots: ")
        print(variable_name + " = " + str(quadraticFormula(a,b,c)[0])) 
        print(variable_name + " = " + str(quadraticFormula(a,b,c)[1]))