/*
Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like . or *.
Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
Example 5:

Input:
s = "mississippi"
p = "mis*is*p*."
Output: false

*/

// Java 
// Time and space complexity are O(p.length() * s.length()).
class Solution {
    public boolean isMatch(String s, String p) {
        // https://leetcode.com/problems/regular-expression-matching/discuss/191830/Java-DP-solution-beats-100-with-explanation
        
        /*
        dp[i][j] denotes if s.substring(0,i) is valid for pattern p.substring(0,j). 
        For example dp[0][0] == true (denoted by y in the matrix) because when 
        s and p are both empty they match. So if we somehow base dp[i+1][j+1] on 
        previos dp[i][j]'s then the result will be dp[s.length()][p.length()]
        
        So what about the first column? for and empty pattern p="" only thing that 
        is valid is an empty string s="" and that is already our dp[0][0] which is 
        true. That means rest of `dp[i][0]' is false.
        */
        boolean dp[][] = new boolean[s.length()+1][p.length()+1];
        
        dp[0][0] = true;
        
        /*
        What about the first row? In other words which pattern p matches empty string s=""? 
        The answer is either an empty pattern p="" or a pattern that can represent an empty 
        string such as p="a*", p="z*" or more interestingly a combiation of them as in p="a*b*c*". 
        Below for loop is used to populate dp[0][j]. Note how it uses previous states by 
        checking dp[0][j-2]
        */
        for (int i = 1; i <= p.length(); i++) {
            if (p.charAt(i-1) == '*') {
                dp[0][i] = dp[0][i-2];
            }
        }
        
        
        //i is to iterate over s
        //j is to iterate over p
        
        /*
        So now we can start our main iteration. It is basically the same, 
        we will iterate all possible s lengths (i) for all possible p lengths (j) 
        and we will try to find a relation based on previous results. 
        Turns out there are two cases.

        Case 1:
        (p.charAt(j-1) == s.charAt(i-1) || p.charAt(j-1) == '.') 
        if the current characters match or pattern has . then the result is determined 
        by the previous state dp[i][j] = dp[i-1][j-1]. 
        
        Don't be confused by the charAt(j-1) charAt(i-1) indexes using a -1 offset that is 
        because our dp array is actually one index bigger than our string and 
        pattern lenghts to hold the initial state dp[0][0].

        Case 2:
        if p.charAt(j-1) == '*' then 
            either it acts as an empty set and the result is 
            dp[i][j] = dp[i][j-2] 
                OR
            Current char of string equals the char preceding * in pattern 
            (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.').
            so the result is 
            dp[i][j] = dp[i-1][j]
        */
        
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                
                if (p.charAt(j-1) == s.charAt(i-1) || p.charAt(j-1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (p.charAt(j-1) == '*') {                    
                    dp[i][j] = dp[i][j-2];
                    
                    if (p.charAt(j-2) == '.' || p.charAt(j-2) == s.charAt(i-1)) {
                        dp[i][j] = dp[i][j] | dp[i-1][j];
                    }
                } else {
                    dp[i][j] = false;
                }
            }
        }
        
        return dp[s.length()][p.length()];
    }
}


// Regular Expression Matching
// Java O(2^n)

class Solution {
    public boolean isMatch(String s, String p) {
        
        if (p.length() == 0) {
            return (s.length() == 0);
        }
        
        if (p.length() > 1 && p.charAt(1) == '*') {
            if (isMatch(s, p.substring(2))) {
                return true;
            }
            
            if (s.length() > 0 && (p.charAt(0) == '.' || p.charAt(0) == s.charAt(0))) {
                return isMatch(s.substring(1), p);
            }
        } else if (s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
            return isMatch(s.substring(1), p.substring(1));
        } 
        
        return false;
    }
}
