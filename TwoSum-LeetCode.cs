//C# find first two nums who sum is target in O(n)

//This solution uses a dictionary. 
//This is definitely not O(n). Looking up a 'value' in dictionary can be expensive.
public class Solution {
    public int[] TwoSum(int[] nums, int target) {
        
        Dictionary<int, int> numDictionary = new Dictionary<int, int>();
        int a, numberToFind;
        int[] result = new int[2];
        
        for (int index = 0; index < nums.Length; index++){
            a = nums[index];
            numberToFind = target - a;
        
            if (numDictionary.ContainsValue(numberToFind)){
                result[1] = index;
                result[0] = numDictionary.FirstOrDefault(x => x.Value == numberToFind).Key;
                return result;
            } else {
                numDictionary.Add(index, a);
            }
        }
        
        return result;
        
    }
}


//This is a better solution. This uses HashSet.
//Assuming lookup in HashSet in O(1), the solution is O(n)
public class Solution {
    public int[] TwoSum(int[] nums, int target) {
        var result = new int[2];
        var mySet = new HashSet<int>();
        int numToFind = -1;
        
        for(int i = 0; i < nums.Length; i++){
            numToFind = target - nums[i];
            
            if (mySet.Contains(numToFind)) {
                result[1] = i;
                break;
            } else {
                mySet.Add(nums[i]);
            }
        }
        
        for(int j = 0; j < nums.Length; j++) {
            if (nums[j] == numToFind) {
                result[0] = j;
                break;
            }
        }
        
        return result;
        
    }
}
