CREATE TABLE RecipeCategory_tbl(RecipeCategoryId_col INTEGER PRIMARY KEY, RecipeCategory_col TEXT);
CREATE TABLE Recipe_tbl(RecipeId_col INTEGER PRIMARY KEY, RecipeName_col TEXT, RecipePhoto_col TEXT, UploadedBy_col TEXT, RecipeCategoryFK_col INTEGER, FOREIGN KEY (RecipeCategoryFK_col) REFERENCES RecipeCategory_tbl(RecipeCategoryId_col));
CREATE TABLE Ingredient_tbl(IngredientId_col INTEGER PRIMARY KEY, IngredientName_col TEXT, IngredAmount_col TEXT, IngredPosition_col INTEGER, RecipeFK_col INTEGER, FOREIGN KEY (RecipeFK_col) REFERENCES Recipe_tbl(RecipeId_col));
CREATE TABLE Instruction_tbl(InstructionId_col INTEGER PRIMARY KEY, InstructionText_col TEXT, InstructPosition_col INTEGER, RecipeFK_col INTEGER, FOREIGN KEY (RecipeFK_col) REFERENCES Recipe_tbl(RecipeId_col));
CREATE TABLE Note_tbl(NoteId_col INTEGER PRIMARY KEY, NoteText_col TEXT, NoteEntityFK_col INTEGER);
CREATE TABLE RecipeTime_tbl(RecipeTimId_col INTEGER PRIMARY KEY, CookDuration_col INTEGER, RecipeIdFK_col INTEGER, FOREIGN KEY (RecipeIdFK_col) REFERENCES Recipe_tbl(RecipeId_col));


INSERT INTO RecipeCategory_tbl(RecipeCategory_col) VALUES ("Dinner");
INSERT INTO RecipeCategory_tbl(RecipeCategory_col) VALUES ("Breakfast");
INSERT INTO RecipeCategory_tbl(RecipeCategory_col) VALUES ("Lunch");

INSERT INTO Recipe_tbl(RecipeCategoryFK_col,UploadedBy_col,RecipePhoto_col,RecipeName_col)
VALUES (1,"Zach","/path/to/photo/","wonderbread");

INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(30,1);
INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(45,1);
INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(60,1);

INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("TUNIC", "1oz", 1, 1);
INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("turnip", "1oz", 2, 1);
INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("tuna", "1oz", 3, 1);

INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT", 1, 1);
INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT", 2, 1);
INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT2", 3, 1);
INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT3", 4, 1);

INSERT INTO Note_tbl(NoteText_col, NoteEntityFK_col)
VALUES ("THIS IS A NOTE", 1);

INSERT INTO Recipe_tbl(RecipeCategoryFK_col,UploadedBy_col,RecipePhoto_col,RecipeName_col)
VALUES (2,"Zach","/path/to/photo/","wonderbread");

INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(10,2);
INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(15,2);
INSERT INTO RecipeTime_tbl(CookDuration_col, RecipeIdFK_col)
VALUES(20,2);

INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("TUNIC", "1oz", 1, 2);
INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("turnip", "1oz", 2, 2);
INSERT INTO Ingredient_tbl(IngredientName_col,IngredAmount_col,IngredPosition_col,RecipeFK_col)
VALUES ("tuna", "1oz", 3, 2);

INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT2", 1, 2);
INSERT INTO Instruction_tbl(InstructionText_col,InstructPosition_col,RecipeFK_col)
VALUES ("THIS INS INSTRUCT3", 2, 2);

INSERT INTO Note_tbl(NoteText_col, NoteEntityFK_col)
VALUES ("THIS IS A NOTE", 2);
INSERT INTO Note_tbl(NoteText_col, NoteEntityFK_col)
VALUES ("THIS IS A NOTE2", 2);
SELECT * FROM Recipe_tbl
Recipe_tbl
  JOIN RecipeCategory_tbl
    ON Recipe_tbl.RecipeCategoryFK_col = RecipeCategory_tbl.RecipeCategoryId_col;

SELECT * FROM Recipe_tbl
  JOIN Ingredient_tbl
    ON Recipe_tbl.RecipeId_col = Ingredient_tbl.RecipeFK_col;

SELECT * FROM Recipe_tbl
  JOIN Instruction_tbl
    ON Recipe_tbl.RecipeId_col = Instruction_tbl.RecipeFK_col;

SELECT * FROM Recipe_tbl
Recipe_tbl
  JOIN Note_tbl
    ON Note_tbl.NoteEntityFK_col = Recipe_tbl.RecipeId_col;

SELECT * FROM Recipe_tbl
Recipe_tbl
  JOIN RecipeTime_tbl
    ON RecipeTime_Tbl.RecipeIdFK_col = Recipe_tbl.RecipeId_col;