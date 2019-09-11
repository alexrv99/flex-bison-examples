// $ANTLR 3.2 Sep 23, 2009 12:02:23 Exp.g 2019-09-11 08:30:24

import org.antlr.runtime.*;

public class ExpParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Number", "WS", "'+'", "'-'", "'*'", "'/'", "'('", "')'"
    };
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;
    public static final int T__6=6;
    public static final int Number=4;
    public static final int T__11=11;
    public static final int WS=5;
    public static final int EOF=-1;
    public static final int T__10=10;

    // delegates
    // delegators


        public ExpParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ExpParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return ExpParser.tokenNames; }
    public String getGrammarFileName() { return "Exp.g"; }



    // $ANTLR start "eval"
    // Exp.g:3:1: eval returns [double value] : exp= additionExp ;
    public final double eval() throws RecognitionException {
        double value = 0.0;

        double exp = 0.0;


        try {
            // Exp.g:4:1: (exp= additionExp )
            // Exp.g:4:3: exp= additionExp
            {
            pushFollow(FOLLOW_additionExp_in_eval17);
            exp=additionExp();

            state._fsp--;

            value = exp;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "eval"


    // $ANTLR start "additionExp"
    // Exp.g:7:1: additionExp returns [double value] : m1= multiplyExp ( '+' m2= multiplyExp | '-' m2= multiplyExp )* ;
    public final double additionExp() throws RecognitionException {
        double value = 0.0;

        double m1 = 0.0;

        double m2 = 0.0;


        try {
            // Exp.g:8:1: (m1= multiplyExp ( '+' m2= multiplyExp | '-' m2= multiplyExp )* )
            // Exp.g:8:3: m1= multiplyExp ( '+' m2= multiplyExp | '-' m2= multiplyExp )*
            {
            pushFollow(FOLLOW_multiplyExp_in_additionExp35);
            m1=multiplyExp();

            state._fsp--;

            value = m1;
            // Exp.g:9:1: ( '+' m2= multiplyExp | '-' m2= multiplyExp )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==6) ) {
                    alt1=1;
                }
                else if ( (LA1_0==7) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // Exp.g:9:3: '+' m2= multiplyExp
            	    {
            	    match(input,6,FOLLOW_6_in_additionExp41); 
            	    pushFollow(FOLLOW_multiplyExp_in_additionExp45);
            	    m2=multiplyExp();

            	    state._fsp--;

            	    value += m2;

            	    }
            	    break;
            	case 2 :
            	    // Exp.g:10:3: '-' m2= multiplyExp
            	    {
            	    match(input,7,FOLLOW_7_in_additionExp51); 
            	    pushFollow(FOLLOW_multiplyExp_in_additionExp55);
            	    m2=multiplyExp();

            	    state._fsp--;

            	    value -= m2;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "additionExp"


    // $ANTLR start "multiplyExp"
    // Exp.g:13:1: multiplyExp returns [double value] : a1= atomExp ( '*' a2= atomExp | '/' a2= atomExp )* ;
    public final double multiplyExp() throws RecognitionException {
        double value = 0.0;

        double a1 = 0.0;

        double a2 = 0.0;


        try {
            // Exp.g:14:1: (a1= atomExp ( '*' a2= atomExp | '/' a2= atomExp )* )
            // Exp.g:14:3: a1= atomExp ( '*' a2= atomExp | '/' a2= atomExp )*
            {
            pushFollow(FOLLOW_atomExp_in_multiplyExp74);
            a1=atomExp();

            state._fsp--;

            value = a1;
            // Exp.g:15:1: ( '*' a2= atomExp | '/' a2= atomExp )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==8) ) {
                    alt2=1;
                }
                else if ( (LA2_0==9) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // Exp.g:15:3: '*' a2= atomExp
            	    {
            	    match(input,8,FOLLOW_8_in_multiplyExp80); 
            	    pushFollow(FOLLOW_atomExp_in_multiplyExp84);
            	    a2=atomExp();

            	    state._fsp--;

            	    value *= a2;

            	    }
            	    break;
            	case 2 :
            	    // Exp.g:16:3: '/' a2= atomExp
            	    {
            	    match(input,9,FOLLOW_9_in_multiplyExp90); 
            	    pushFollow(FOLLOW_atomExp_in_multiplyExp94);
            	    a2=atomExp();

            	    state._fsp--;

            	    value /= a2;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "multiplyExp"


    // $ANTLR start "atomExp"
    // Exp.g:20:1: atomExp returns [double value] : (n= Number | '(' exp= additionExp ')' );
    public final double atomExp() throws RecognitionException {
        double value = 0.0;

        Token n=null;
        double exp = 0.0;


        try {
            // Exp.g:21:1: (n= Number | '(' exp= additionExp ')' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==Number) ) {
                alt3=1;
            }
            else if ( (LA3_0==10) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Exp.g:21:3: n= Number
                    {
                    n=(Token)match(input,Number,FOLLOW_Number_in_atomExp114); 
                    value = Double.parseDouble((n!=null?n.getText():null));

                    }
                    break;
                case 2 :
                    // Exp.g:22:3: '(' exp= additionExp ')'
                    {
                    match(input,10,FOLLOW_10_in_atomExp120); 
                    pushFollow(FOLLOW_additionExp_in_atomExp124);
                    exp=additionExp();

                    state._fsp--;

                    match(input,11,FOLLOW_11_in_atomExp126); 
                    value = exp;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "atomExp"

    // Delegated rules


 

    public static final BitSet FOLLOW_additionExp_in_eval17 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplyExp_in_additionExp35 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_6_in_additionExp41 = new BitSet(new long[]{0x0000000000000410L});
    public static final BitSet FOLLOW_multiplyExp_in_additionExp45 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_7_in_additionExp51 = new BitSet(new long[]{0x0000000000000410L});
    public static final BitSet FOLLOW_multiplyExp_in_additionExp55 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_atomExp_in_multiplyExp74 = new BitSet(new long[]{0x0000000000000302L});
    public static final BitSet FOLLOW_8_in_multiplyExp80 = new BitSet(new long[]{0x0000000000000410L});
    public static final BitSet FOLLOW_atomExp_in_multiplyExp84 = new BitSet(new long[]{0x0000000000000302L});
    public static final BitSet FOLLOW_9_in_multiplyExp90 = new BitSet(new long[]{0x0000000000000410L});
    public static final BitSet FOLLOW_atomExp_in_multiplyExp94 = new BitSet(new long[]{0x0000000000000302L});
    public static final BitSet FOLLOW_Number_in_atomExp114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_atomExp120 = new BitSet(new long[]{0x0000000000000410L});
    public static final BitSet FOLLOW_additionExp_in_atomExp124 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_atomExp126 = new BitSet(new long[]{0x0000000000000002L});

}