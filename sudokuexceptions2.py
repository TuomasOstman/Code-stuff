import sys

a = "123456789123456789123456789123456789123456789123456789123456789123456789"
class SudokuException(Exception):
    def __init__(self, message):
        self.message = message;
    def __str__(self):
        if(self.message == ''):
            return "[SudokuException]"
        else:
            a = "[SudokuException] " + self.message
            return a
class SudokuTypeError(SudokuException):
    def __init__(self, message):
        self.message = message;
    def __str__(self):
        if(self.message == ''):
            return "[SudokuTypeError]"
        else:
            a = "[SudokuTypeError] " + self.message
            return a
class SudokuValueError(SudokuException):
    def __init__(self, message):
        self.message = message;
    def __str__(self):
        if(self.message == ''):
            return "[SudokuValueError]"
        else:
            a = "[SudokuValueError] " + self.message
            return a
def printSudoku(a):
    
    if type(a) != str:
        b = 'Illegal type ' + str(type(a))
        raise SudokuTypeError(b)
    if len(a) != 81:
        b = 'Illegal length ' + str(len(a)) + ' != 81'
        raise SudokuTypeError(b)
    for i in range(0, len(a)):
        if a[i] == 0:
            b = 'The number ' + a[i] + ' is not legal in Sudoku'
            raise SudokuValueError(b)
        if a[i] not in ['1','2','3','4','5','6','7','8','9']:
            b = 'The character ' + a[i] + ' does not represent a digit'
            raise SudokuValueError(b)
    b = 0
    for i in range(0, 19):
        if(i == 0 or i == 6 or i == 12 or i == 18):
            print('#####################################')
        if(i == 2 or i == 4 or i == 8 or i == 10 or i == 14 or i == 16):
            print('#---+---+---#---+---+---#---+---+---#')
        if(i == 1 or i == 3 or i == 5 or i == 7 or i == 9 or i == 11 or i == 13 or i == 15 or i == 17):
            print('# ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' # ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' # ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' | ', end = '')
            print(a[b], end = '')
            b += 1
            print(' #')
try:
  try:
    try:
      printSudoku(a)
    except Exception as e:
      print("Caught an Exception:", Exception.__str__(e))
      raise e
  except SudokuException as e:
    print("Caught a SudokuException:", SudokuException.__str__(e))
    raise e
except SudokuTypeError as e:
  print("Caught a SudokuTypeError:", e)
except SudokuValueError as e:
  print("Caught a SudokuValueError:", e)
