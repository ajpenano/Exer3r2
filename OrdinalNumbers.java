public class OrdinalNumbers {
    
	public String getOrdinal(int number) {
        
        int lastTwoDigits = number % 100;
        int lastDigit = number % 10;
        switch (lastDigit) {
            case 1:
                if (lastTwoDigits != 11) {
                    return number + "st";
                }
                break;
            case 2:
                if (lastTwoDigits != 12) {
                    return number + "nd";
                }
                break;
            case 3:
                if (lastTwoDigits != 13) {
                    return number + "rd";
                }
                break;
        }
        return number + "th";
    }
}