from abc import ABCMeta, abstractmethod
from Rectangle import *

class Piece(Rectangle):
    ___metaclass___ = ABCMeta
    WIDTH = 80
    HEIGHT = 80
    self.__color = None
    self.__type = None
    self.__image = None

    def __init__(type, color, row, col):
        __color = color
        __type = type
        Rectangle.__init__(col-1*WIDTH, x-1*HEIGHT, WIDTH, HEIGHT)
        __image
    
    @abstractmethod
    def getType(): pass
    @abstractmethod
    def isLegalMove(): pass