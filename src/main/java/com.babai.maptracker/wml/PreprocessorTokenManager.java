/* PreprocessorTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. PreprocessorTokenManager.java */
package wml;
import java.util.*;
import java.util.logging.*;
import java.io.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class PreprocessorTokenManager implements PreprocessorConstants {
        private boolean showLogs = false;
        private final Logger tL = Logger.getLogger("preprocessor.token");

        public void setLogs(boolean showLogs) {
                this.showLogs = showLogs;
        }
        public void debugPrint(String s) {
                if (showLogs) {
                        tL.info(s);
                }
        }

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 35:
         return jjMoveStringLiteralDfa1_0(0x7cL);
      case 61:
         return jjStopAtPos(0, 9);
      case 123:
         return jjStopAtPos(0, 7);
      case 125:
         return jjStopAtPos(0, 8);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa2_0(active0, 0x4L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x28L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x10L);
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x40L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x4L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x10L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x68L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa4_0(active0, 0x78L);
      case 102:
         return jjMoveStringLiteralDfa4_0(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 100:
         return jjMoveStringLiteralDfa5_0(active0, 0x8L);
      case 101:
         return jjMoveStringLiteralDfa5_0(active0, 0x50L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x24L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x8L);
      case 102:
         if ((active0 & 0x10L) != 0L)
            return jjStopAtPos(5, 4);
         else if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(5, 5);
         else if ((active0 & 0x40L) != 0L)
            return jjStopAtPos(5, 6);
         break;
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x4L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x4L) != 0L)
            return jjStopAtPos(6, 2);
         break;
      case 102:
         if ((active0 & 0x8L) != 0L)
            return jjStopAtPos(6, 3);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 14;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 12)
                        kind = 12;
                     { jjCheckNAddTwoStates(11, 12); }
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 15)
                        kind = 15;
                  }
                  else if ((0x100000200L & l) != 0L)
                  {
                     if (kind > 10)
                        kind = 10;
                  }
                  else if (curChar == 33)
                     { jjCheckNAddStates(0, 2); }
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 11)
                        kind = 11;
                     { jjCheckNAdd(6); }
                  }
                  else if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 1:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 1)
                     kind = 1;
                  break;
               case 3:
                  if (curChar == 10 && kind > 1)
                     kind = 1;
                  break;
               case 4:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 5:
                  if ((0x100000200L & l) != 0L && kind > 10)
                     kind = 10;
                  break;
               case 6:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 11)
                     kind = 11;
                  { jjCheckNAdd(6); }
                  break;
               case 8:
                  if ((0x2400L & l) != 0L && kind > 15)
                     kind = 15;
                  break;
               case 9:
                  if (curChar == 10 && kind > 15)
                     kind = 15;
                  break;
               case 10:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 12)
                     kind = 12;
                  { jjCheckNAddTwoStates(11, 12); }
                  break;
               case 12:
                  if (curChar == 46)
                     { jjCheckNAdd(13); }
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 12)
                     kind = 12;
                  { jjCheckNAdd(13); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 13)
                        kind = 13;
                  }
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 11)
                        kind = 11;
                     { jjCheckNAdd(6); }
                  }
                  break;
               case 1:
                  { jjAddStates(0, 2); }
                  break;
               case 6:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 11)
                     kind = 11;
                  { jjCheckNAdd(6); }
                  break;
               case 7:
                  if ((0x7fffffe07fffffeL & l) != 0L && kind > 13)
                     kind = 13;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(0, 2); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 14 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, "\43\144\145\146\151\156\145", "\43\145\156\144\144\145\146", 
"\43\151\146\144\145\146", "\43\145\156\144\151\146", "\43\165\156\144\145\146", "\173", "\175", "\75", 
null, null, null, null, null, null, };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {
   1, 2, 4, 
};

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }
   image = jjimage;
   image.setLength(0);
   jjimageLen = 0;

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         TokenLexicalActions(matchedToken);
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      case 2 :
        image.append(jjstrLiteralImages[2]);
        lengthOfMatch = jjstrLiteralImages[2].length();
                                  debugPrint("DEFINE");
         break;
      case 3 :
        image.append(jjstrLiteralImages[3]);
        lengthOfMatch = jjstrLiteralImages[3].length();
                                  debugPrint("ENDDEF");
         break;
      case 7 :
        image.append(jjstrLiteralImages[7]);
        lengthOfMatch = jjstrLiteralImages[7].length();
                                          debugPrint("LBR");
         break;
      case 8 :
        image.append(jjstrLiteralImages[8]);
        lengthOfMatch = jjstrLiteralImages[8].length();
                                          debugPrint("RBR");
         break;
      case 9 :
        image.append(jjstrLiteralImages[9]);
        lengthOfMatch = jjstrLiteralImages[9].length();
                                          debugPrint("EQL");
         break;
      case 10 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                  debugPrint("Space");
         break;
      case 11 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                                                  debugPrint("STRING");
         break;
      case 12 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                                          debugPrint("NUMBER");
         break;
      case 13 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                                                  debugPrint("LETTER");
         break;
      case 14 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                                                          debugPrint("DIGIT");
         break;
      case 15 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                                                                  debugPrint("EOL");
         break;
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public PreprocessorTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public PreprocessorTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 14; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xfffdL, 
};
static final long[] jjtoSkip = {
   0x2L, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[14];
    private final int[] jjstateSet = new int[2 * 14];
    private final StringBuilder jjimage = new StringBuilder();
    private StringBuilder image = jjimage;
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;
}
