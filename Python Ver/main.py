from tkinter import *

class Game(Frame):
    
    def say_hi(self):
        print ("hi there, everyone!")


    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.pack()
        self.createWidgets()

def main():
    root = Tk()
    app = Game(master=root)
    app.mainloop()
    root.destroy()    
    

if __name__ == "__main__": main()
