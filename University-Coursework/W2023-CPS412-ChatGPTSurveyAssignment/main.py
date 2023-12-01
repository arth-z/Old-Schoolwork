"""
The following piece of code was created for the CPS412 Assignment 1 (ChatGPT survey and report)
It:
 - collects information from a predetermined .csv generated from a specific Google Form found within the directory
 - collates the information into some relevant findings using matplotlib (this is not default on Python installs)

Note: the program is quite messy and depends on a lot of 1:1 correspondences between lists of strings (questions), lists of dictionaries (answers to questions), etc. 
It's also made in mind for *this* specific Google Form: https://forms.gle/G6rppSEWg4VRdBNr7

Click the 'x' icon (close the windows) to switch the graph
"""

# does not come with python base install - to run the program through this .py, install these first using pip
# use CMD as administrator and execute the following commands:
# pip install pandas
# pip install matplotlib
# pip install numpy
import pandas as pd
import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np


# class describing a single completion of a survey
class SurveyCompletion:

  possibleQuestions = dict()  # 'template' dictionary for all responses to follow

  # define the possible questions before the constructor so that created objects adhere to this format
  # possible questions defined as a dict with the keys as questions and the values (answers) initially as null for easy assignment in constructor
  def setPossibleQuestions(questions):
    for string in questions:
      SurveyCompletion.possibleQuestions[string] = None

  # python convention: __ is private variable
  def __init__(self):
    self.__response_dict = dict(SurveyCompletion.possibleQuestions)  # clone the possible responses

  # getter, mutable object
  def response_dict(self):
    return self.__response_dict


# takes in a list of SurveyCompletions (completionArray) and outputs a dictionary with the total count of different responses
# output will look like, for example: {"Yes": 50, "No:", 30} for a question "Have you used ChatGPT before?"
def collectResponsesToQuestion(questionString, completionArray):
  answersToQuestion = dict()
  for completion in completionArray:
    individualResponse = completion.response_dict()[questionString]
    if individualResponse not in answersToQuestion:
      answersToQuestion[individualResponse] = 1
    else:
      answersToQuestion[individualResponse] += 1
  return answersToQuestion


# takes in a list of SurveyCompletions (completionArray) and outputs a dictionary with the total count of different responses
# however, where this method differs by the first is that it collects only the responses of the specific SurveyCompletions
# that answered a specific answer to specific question - this lets us home in on the results of people who answered a specific question in a certain way
# this method is used to generate the data needed for stacked bar graphs
def collectResponsesToQuestionByOtherResponse(depQuestion, wantedIndepAnswer,
                                              indepQuestion, completionArray):
  answersToQuestion = dict()  # dict to return, we'll be adding to this later

  # iterate through completions
  for completion in completionArray:
    # search for desired independent answer using indep question
    indepAnswer = completion.response_dict()[indepQuestion]
    if indepAnswer == wantedIndepAnswer:
      wantedResponse = completion.response_dict()[depQuestion]
      # only log the wanted responses of the SurveyCompletions that answered our wanted independent question a certain way
      if wantedResponse not in answersToQuestion:
        answersToQuestion[wantedResponse] = 1
      else:
        answersToQuestion[wantedResponse] += 1

  return answersToQuestion  # intended output: a list of dicts with stuff like {"Disagree": 50, "Agree": 40} where the key-value pairs collected in the dictionary are only from a specific answer to a specific question (indep question and indep answer)


# clean up some responses
# betting on parameter to be passed in is an array of all the questions on our survey, with fixed indices
# essentially hard coded method to clean up responses based on indexes in our google form
def responseCleanUp(surveyCompletions, questionsReference):

  # hunt for canadian university keywords to standardise responses
  uoftKeywords = "oft"
  queensKeywords = "queen"
  masterKeywords = "master"
  waterlooKeywords = "waterloo"
  yorkKeywords = "york"
  laurierKeywords = "laurier"
  tmuKeywords = "yes"

  # establish a 1:1 between altNames and properNames to make iteration easier
  altNames = [
    uoftKeywords, queensKeywords, masterKeywords, waterlooKeywords,
    yorkKeywords, laurierKeywords, tmuKeywords
  ]
  properNames = [
    "University of Toronto", "Queen's University", "McMaster University",
    "University of Waterloo", "York University", "Wilfred Laurier University",
    "Toronto Metropolitan University"
  ]

  for completion in surveyCompletions:

    # correct age misaligned space
    age = completion.response_dict()[questionsReference[2]]
    if "18 -24" in age:
      completion.response_dict()[questionsReference[2]] = "18 - 24 years old"

    # standardise university responses using keywords and set responses with keywords in them to 'proper' names (question 8 is the university question)
    attendedUniversity = completion.response_dict()[
      questionsReference[8]].lower().strip()
    for i in range(len(
        altNames)):  # iterate through altNames and properNames - both are 1:1
      if altNames[i] in attendedUniversity or properNames[i].lower(
      ) in attendedUniversity:
        correctedUniversity = properNames[i]
        completion.response_dict()[questionsReference[8]] = correctedUniversity

  # after above is done, and all common non-standardised responses are corrected,
  # we can now standardise the rest of the responses to simply "other"
  for completion in surveyCompletions:
    if completion.response_dict()[questionsReference[8]] not in properNames:
      completion.response_dict()[
        questionsReference[8]] = "All of the above/Other"

  # another thing: cleaning up that "how do you use chatGPT question"
  # by standardising other responses to a generic option
  question12GenericAnswers = [
    "As a research aid for papers, assignments, and finding scholarly information",
    "As a general aid for answering questions, solving problems, and looking things up",
    "As a creativity tool", "As a form of entertainment"
  ]

  # iterate through completions, change dissenting responses to a standard response
  for completion in surveyCompletions:
    if completion.response_dict()[
        questionsReference[12]] not in question12GenericAnswers:
      completion.response_dict()[
        questionsReference[12]] = "All of the above/Other"

  return surveyCompletions


  
# program actually starts here

# try to pass in user input to read the .csv file (of our findings), catch errors when they enter invalid files
invalidInput = True
while invalidInput:
  try:
    print(
      "Enter the filename of your findings (a .csv file), case sensitive, include extensions. Make sure this program and the file share the same folder."
    )
    print(
      "To default to 'CPS412 POLL - CHATGPT  (Responses) - Form Responses 1.csv', provided in the same directory as the program, type in 'default'."
    )
    fileName = input()
    if fileName.lower() == "default":
      fileName = "CPS412 POLL - CHATGPT  (Responses) - Form Responses 1.csv"

    survey_df = pd.read_csv(fileName)  # open the file

    invalidInput = False
  except FileNotFoundError:  # when user enters an invalid filename
    print("File couldn't be found. Try again.")

completions = []  # list of SurveyCompletion items

# define possible questions
# each index in the questions list corresponds to a column in the spreadsheet 1:1, indexed from 0
# so to access the 'question' of column 1, which is 'timestamp', you access questions[0]
questions = list(survey_df.columns)
SurveyCompletion.setPossibleQuestions(questions)

# now, create new SurveyCompletion objects and modify their dicts accordingly to the responses list
# here their dicts are key-value question-answers
for i in range(survey_df.shape[0]):

  # init new object
  completion = SurveyCompletion()
  response_dict = completion.response_dict()  # reference to mutable object

  # assigning answers to questions
  for question in questions:  # log responses by accessing cell value by column (header) and row number (i)
    response_dict[question] = survey_df[question][i]

  completions.append(completion)  # add to list of SurveyCompletions

completions = responseCleanUp(completions, questions)  # clean up responses

results = []  # array for responses to all questions

# gather all responses to questions in list of questions, add them to results array
# that way we have an array of dicts, where the index matches a question, and the dicts show numbers of responses
for questionString in questions:
  questionResponses = collectResponsesToQuestion(questionString, completions)
  results.append(questionResponses)

'''
alright, now for the fun part.
the objective of the program is to create some good ol stacked bar graphs where each different stack on the bar represents
a percentage of some other third determinant variable. For example: a bar graph with responses to a question labelled
"Agree", "Disagree", etc., but each bar on each column is appropriately coloured with a percentage of some other demographic
(faculty, age, university, whatever)
'''

# first we need a method though
# failsafe method to guard against KeyErrors - turned out to be a bit of a problem when your program depends on
# all completions having covered all different possible responses, especially when dividing things into smaller sections
# key error would occur when say, nobody in the faculty of arts ever answered "Strongly disagree" to question 13 or something


# inputs: a list of strings (valid answers to a question),
#         a single integer to access answers to a question,
#         a list of many dict results (dicts of answers to the question at their index, with keys as answers and values the number of people who answered that way)
# output: a list of integers, elements being the number of answers, mapped by index to answerList. the answers are to the question determined by questionIndex. all answers, including ones where there may have been zero responses, are included
def getResultsAnswers(answerList, questionIndex, resultList):

  listToReturn = []

  for answer in answerList:  # iterate through all answers in answer list
    try:
      listToReturn.append(
        resultList[questionIndex][answer]
      )  # try to add the resultList's integer value matching to the key of the answer (number of people who answered this option) to the list
    except KeyError:
      resultList[questionIndex][answer] = 0
      listToReturn.append(resultList[questionIndex][answer])

  return listToReturn



  
# actual graph-making part - alot of the code here is repeated, except for this beginning part making the faculty stuff
# so expect less comments from here on out
# collating responses by faculty

facultyQuestion = questions[4]

artResults = []
commResults = []
engiResults = []
sciResults = []
trsmResults = []

artAnswer = "Faculty of Arts (English, History, Philosophy, Politics and Governance, Sociology etc.)"
commAnswer = "Faculty of Community Services (Occupational Health and Safety, Public Health etc.)"
engiAnswer = "Faculty of Engineering and Architectural Science (Aerospace Engineering, Architectural Science etc.)"
sciAnswer = "Faculty of Science (Biology, Chemistry, Computer Science etc.)"
trsmAnswer = "Ted Rogers School of Management (Accounting, Business Management, Hospitality etc.)"

# establishing a 1:1 between results and answers so I can iterate better
resultsByFaculty = [
  artResults, commResults, engiResults, sciResults, trsmResults
]
answersByFaculty = [artAnswer, commAnswer, engiAnswer, sciAnswer, trsmAnswer]

# for loop and a list to get answers sorted by faculty responses
for i in range(len(resultsByFaculty)):
  for questionString in questions:
    facultyAnswerDict = collectResponsesToQuestionByOtherResponse(
      questionString, answersByFaculty[i], facultyQuestion, completions)

    resultsByFaculty[i].append(facultyAnswerDict)

# ok, here we go - creating a bar graph for "do you use chatGPT", organised by faculty

gptUsageAnswers = ["Yes", "No"]

# note: question 6 is the "do you use chatGPT" question, hence it being used here
# our previous method is called to account for 0 responses in certain answers (like nobody in art puts 'No' for some reason, guards against KeyError)
gptUsageArt = np.array(getResultsAnswers(gptUsageAnswers, 6, artResults))
gptUsageComm = np.array(getResultsAnswers(gptUsageAnswers, 6, commResults))
gptUsageEngi = np.array(getResultsAnswers(gptUsageAnswers, 6, engiResults))
gptUsageSci = np.array(getResultsAnswers(gptUsageAnswers, 6, sciResults))
gptUsageTRSM = np.array(getResultsAnswers(gptUsageAnswers, 6, trsmResults))

# creating the bar graph (stacked)
plt.bar(gptUsageAnswers, gptUsageArt, color='r')
plt.bar(gptUsageAnswers, gptUsageComm, bottom=gptUsageArt, color='b')
plt.bar(gptUsageAnswers,
        gptUsageEngi,
        bottom=gptUsageArt + gptUsageComm,
        color='g')
plt.bar(gptUsageAnswers,
        gptUsageSci,
        bottom=gptUsageArt + gptUsageComm + gptUsageEngi,
        color='y')
plt.bar(gptUsageAnswers,
        gptUsageTRSM,
        bottom=gptUsageArt + gptUsageComm + gptUsageEngi + gptUsageSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title("Have you used ChatGPT before?")
plt.show()

# ok, here we go - creating a bar graph for "how do you use chatGPT", organised by faculty
howGPTusedAnswers = [
  "As a research aid for papers, assignments, and finding scholarly information",
  "As a general aid for answering questions, solving problems, and looking things up",
  "As a creativity tool", "As a form of entertainment",
  "All of the above/Other"
]

# note: question 12 is the "how do you use chatGPT" question, hence it being used here
howGPTArt = np.array(getResultsAnswers(howGPTusedAnswers, 12, artResults))
howGPTComm = np.array(getResultsAnswers(howGPTusedAnswers, 12, commResults))
howGPTEngi = np.array(getResultsAnswers(howGPTusedAnswers, 12, engiResults))
howGPTSci = np.array(getResultsAnswers(howGPTusedAnswers, 12, sciResults))
howGPTTRSM = np.array(getResultsAnswers(howGPTusedAnswers, 12, trsmResults))

# same as before
plt.bar(howGPTusedAnswers, howGPTArt, color='r')
plt.bar(howGPTusedAnswers, howGPTComm, bottom=howGPTArt, color='b')
plt.bar(howGPTusedAnswers,
        howGPTEngi,
        bottom=howGPTArt + howGPTComm,
        color='g')
plt.bar(howGPTusedAnswers,
        howGPTSci,
        bottom=howGPTArt + howGPTComm + howGPTEngi,
        color='y')
plt.bar(howGPTusedAnswers,
        howGPTTRSM,
        bottom=howGPTArt + howGPTComm + howGPTEngi + howGPTSci,
        color='m')

plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
bars = ("Research Aid", "General Aid", "Creative Tool", "Entertainment", "Other")
y_pos = np.arange(len(bars))
plt.xticks(y_pos, bars, color='Black',rotation=0,fontweight='light',fontsize='8',horizontalalignment='center')
plt.ylabel("Responses")
plt.title("What do you use ChatGPT for?")
plt.show()

# more bar graphs, more of the same stuff
gptStandardAnswers = [
  "Strongly disagree", "Disagree", "Neutral", "Agree", "Strongly agree"
]

gptAidArt = np.array(getResultsAnswers(gptStandardAnswers, 7, artResults))
gptAidComm = np.array(getResultsAnswers(gptStandardAnswers, 7, commResults))
gptAidEngi = np.array(getResultsAnswers(gptStandardAnswers, 7, engiResults))
gptAidSci = np.array(getResultsAnswers(gptStandardAnswers, 7, sciResults))
gptAidTRSM = np.array(getResultsAnswers(gptStandardAnswers, 7, trsmResults))

plt.bar(gptStandardAnswers, gptAidArt, color='r')
plt.bar(gptStandardAnswers, gptAidComm, bottom=gptAidArt, color='b')
plt.bar(gptStandardAnswers,
        gptAidEngi,
        bottom=gptAidArt + gptAidComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptAidSci,
        bottom=gptAidArt + gptAidComm + gptAidEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptAidTRSM,
        bottom=gptAidArt + gptAidComm + gptAidEngi + gptAidSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title(
  "ChatGPT should be used by students as an AID on assignments and tests")
plt.show()

NoGPTTestArt = np.array(getResultsAnswers(gptStandardAnswers, 11, artResults))
NoGPTTestComm = np.array(getResultsAnswers(gptStandardAnswers, 11,
                                           commResults))
NoGPTTestEngi = np.array(getResultsAnswers(gptStandardAnswers, 11,
                                           engiResults))
NoGPTTestSci = np.array(getResultsAnswers(gptStandardAnswers, 11, sciResults))
NoGPTTestTRSM = np.array(getResultsAnswers(gptStandardAnswers, 11,
                                           trsmResults))

plt.bar(gptStandardAnswers, NoGPTTestArt, color='r')
plt.bar(gptStandardAnswers, NoGPTTestComm, bottom=NoGPTTestArt, color='b')
plt.bar(gptStandardAnswers,
        NoGPTTestEngi,
        bottom=NoGPTTestArt + NoGPTTestComm,
        color='g')
plt.bar(gptStandardAnswers,
        NoGPTTestSci,
        bottom=NoGPTTestArt + NoGPTTestComm + NoGPTTestEngi,
        color='y')
plt.bar(gptStandardAnswers,
        NoGPTTestTRSM,
        bottom=NoGPTTestArt + NoGPTTestComm + NoGPTTestEngi + NoGPTTestSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title("ChatGPT solutions should not be used as direct answers for tests")
plt.show()

gptPlaigirismArt = np.array(
  getResultsAnswers(gptStandardAnswers, 10, artResults))
gptPlaigirismComm = np.array(
  getResultsAnswers(gptStandardAnswers, 10, commResults))
gptPlaigirismEngi = np.array(
  getResultsAnswers(gptStandardAnswers, 10, engiResults))
gptPlaigirismSci = np.array(
  getResultsAnswers(gptStandardAnswers, 10, sciResults))
gptPlaigirismTRSM = np.array(
  getResultsAnswers(gptStandardAnswers, 10, trsmResults))

plt.bar(gptStandardAnswers, gptPlaigirismArt, color='r')
plt.bar(gptStandardAnswers,
        gptPlaigirismComm,
        bottom=gptPlaigirismArt,
        color='b')
plt.bar(gptStandardAnswers,
        gptPlaigirismEngi,
        bottom=gptPlaigirismArt + gptPlaigirismComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptPlaigirismSci,
        bottom=gptPlaigirismArt + gptPlaigirismComm + gptPlaigirismEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptPlaigirismTRSM,
        bottom=gptPlaigirismArt + gptPlaigirismComm + gptPlaigirismEngi +
        gptPlaigirismSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title("Using ChatGPT on assignments and tests is plagiarism")
plt.show()

gptEnforcementArt = np.array(
  getResultsAnswers(gptStandardAnswers, 15, artResults))
gptEnforcementComm = np.array(
  getResultsAnswers(gptStandardAnswers, 15, commResults))
gptEnforcementEngi = np.array(
  getResultsAnswers(gptStandardAnswers, 15, engiResults))
gptEnforcementSci = np.array(
  getResultsAnswers(gptStandardAnswers, 15, sciResults))
gptEnforcementTRSM = np.array(
  getResultsAnswers(gptStandardAnswers, 15, trsmResults))

plt.bar(gptStandardAnswers, gptEnforcementArt, color='r')
plt.bar(gptStandardAnswers,
        gptEnforcementComm,
        bottom=gptEnforcementArt,
        color='b')
plt.bar(gptStandardAnswers,
        gptEnforcementEngi,
        bottom=gptEnforcementArt + gptEnforcementComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptEnforcementSci,
        bottom=gptEnforcementArt + gptEnforcementComm + gptEnforcementEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptEnforcementTRSM,
        bottom=gptEnforcementArt + gptEnforcementComm + gptEnforcementEngi +
        gptEnforcementSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title(
  "Post-secondary academic integrity policies should apply to ChatGPT usage"
)
plt.show()

gptReferenceArt = np.array(
  getResultsAnswers(gptStandardAnswers, 14, artResults))
gptReferenceComm = np.array(
  getResultsAnswers(gptStandardAnswers, 14, commResults))
gptReferenceEngi = np.array(
  getResultsAnswers(gptStandardAnswers, 14, engiResults))
gptReferenceSci = np.array(
  getResultsAnswers(gptStandardAnswers, 14, sciResults))
gptReferenceTRSM = np.array(
  getResultsAnswers(gptStandardAnswers, 14, trsmResults))

plt.bar(gptStandardAnswers, gptReferenceArt, color='r')
plt.bar(gptStandardAnswers,
        gptReferenceComm,
        bottom=gptReferenceArt,
        color='b')
plt.bar(gptStandardAnswers,
        gptReferenceEngi,
        bottom=gptReferenceArt + gptReferenceComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptReferenceSci,
        bottom=gptReferenceArt + gptReferenceComm + gptReferenceEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptReferenceTRSM,
        bottom=gptReferenceArt + gptReferenceComm + gptReferenceEngi +
        gptReferenceSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title(
  "Students should be permitted to use ChatGPT as an academic reference")
plt.show()

gptCreateArt = np.array(getResultsAnswers(gptStandardAnswers, 9, artResults))
gptCreateComm = np.array(getResultsAnswers(gptStandardAnswers, 9, commResults))
gptCreateEngi = np.array(getResultsAnswers(gptStandardAnswers, 9, engiResults))
gptCreateSci = np.array(getResultsAnswers(gptStandardAnswers, 9, sciResults))
gptCreateTRSM = np.array(getResultsAnswers(gptStandardAnswers, 9, trsmResults))

plt.bar(gptStandardAnswers, gptCreateArt, color='r')
plt.bar(gptStandardAnswers, gptCreateComm, bottom=gptCreateArt, color='b')
plt.bar(gptStandardAnswers,
        gptCreateEngi,
        bottom=gptCreateArt + gptCreateComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptCreateSci,
        bottom=gptCreateArt + gptCreateComm + gptCreateEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptCreateTRSM,
        bottom=gptCreateArt + gptCreateComm + gptCreateEngi + gptCreateSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title("ChatGPT sparks creativity")
plt.show()

# creating a bar graph for trust of GPTZero

gptZeroArt = np.array(getResultsAnswers(gptStandardAnswers, 13, artResults))
gptZeroComm = np.array(getResultsAnswers(gptStandardAnswers, 13, commResults))
gptZeroEngi = np.array(getResultsAnswers(gptStandardAnswers, 13, engiResults))
gptZeroSci = np.array(getResultsAnswers(gptStandardAnswers, 13, sciResults))
gptZeroTRSM = np.array(getResultsAnswers(gptStandardAnswers, 13, trsmResults))

plt.bar(gptStandardAnswers, gptZeroArt, color='r')
plt.bar(gptStandardAnswers, gptZeroComm, bottom=gptZeroArt, color='b')
plt.bar(gptStandardAnswers,
        gptZeroEngi,
        bottom=gptZeroArt + gptZeroComm,
        color='g')
plt.bar(gptStandardAnswers,
        gptZeroSci,
        bottom=gptZeroArt + gptZeroComm + gptZeroEngi,
        color='y')
plt.bar(gptStandardAnswers,
        gptZeroTRSM,
        bottom=gptZeroArt + gptZeroComm + gptZeroEngi + gptZeroSci,
        color='m')
plt.legend(["Art", "Community", "Engineering", "Science", "Business"])
plt.ylabel("Responses")
plt.title("GPTZero is reliable and should be used to detect plagiarism")
plt.show()



"""
# collating responses by "are you a comp sci student or not"
compSciQuestion = questions[5]

notCSResults= []
yesCSResults = []

for completion in completions:
  for questionString in questions:
    csAnswerDict = collectResponsesToQuestionByOtherResponse(
      questionString, "No", compSciQuestion, completions)
    notCSResults.append(csAnswerDict)

for completion in completions:
  for questionString in questions:
    csAnswerDict = collectResponsesToQuestionByOtherResponse(
      questionString, "Yes", compSciQuestion, completions)
    yesCSResults.append(csAnswerDict)

# gpt usage by comp sci studentness
gptUsageNotCS = np.array(getResultsAnswers(gptUsageAnswers, 6, notCSResults))
gptUsageCS = np.array(getResultsAnswers(gptUsageAnswers, 6, yesCSResults))

plt.bar(gptUsageAnswers, gptUsageNotCS, color='r')
plt.bar(gptUsageAnswers, gptUsageCS, bottom=gptUsageNotCS, color='b')
plt.legend(gptUsageAnswers)
plt.ylabel("Responses")
plt.title("Organised by CS: Have you used ChatGPT before?")
plt.show()
"""
