import java.util.*;

public class BankApplication {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    
    static double rateA;
    static double rateB;
    static double rateC;
    static double rateD;
    static List<User> users = new ArrayList<>();
    static List<User> originalUsers = new ArrayList<>();

    static String[] names = {"Aras", "Derya", "Arda", "Sinem", "Omer", "Mehmet", "Ali", "Osman", "Kenan", "Fuat", "Deniz", "Volkan", "Yusuf", "Halil", "Mustafa"};
    static String[] surnames = {"Soylu", "Polat", "Akin", "Yildirim", "Durur", "Bastem", "Keskin", "Demir", "Karaca", "Turan", "Hernandez", "Arikan", "Balaban", "Can", "Demirel"};

    public static void main(String[] args) 
    {
        initRates();
        menuLoop();
    }

    static void initRates() 
    {
        rateA = randRate();
        rateB = randRate();
        rateC = randRate();
        rateD = randRate();
        System.out.println("Welcome to the bank!");
        System.out.println();
        printRates();
    }

    static double randRate() 
    {
        return 0.2 + random.nextDouble() * (5.0 - 0.2);
    }

    static void printRates() 
    {
        System.out.println("Current conversion rates: ");
        System.out.println("A: " + rateA);
        System.out.println("B: " + rateB);
        System.out.println("C: " + rateC);
        System.out.println("D: " + rateD);
        System.out.println();
    }

    static void menuLoop() 
    {
        boolean check = true;
        while (check) 
        {
            System.out.println("What do you want to do?\n1. Generate random users\n2. List users\n3. Show user with ID\n4. Set conversion rates\n5. Sort users\n6. Reset to the original unsorted array\n0. Exit");
            System.out.print("Option: ");
            int choice = scanner.nextInt();
            switch (choice) 
            {
                case 1: genUsers(); break;
                case 2: listUsers(); break;
                case 3: 
                    try 
                    {
                        showUserById();
                    } 
                    catch (NoSuchElementException e) 
                    {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4: setRates(); break;
                case 5: sortUsers(); break;
                case 6: resetUsers(); break;
                case 0: System.out.println("Bye!");
                    check = false;
                default: System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    static void genUsers() 
    {
        System.out.print("Generate how many?: ");
        int n = scanner.nextInt();
        users.clear();

        for (int i = 0; i < n; i++)
        {
            String id = generateId();
            String name = names[random.nextInt(names.length)];
            String surname = surnames[random.nextInt(surnames.length)];
            User user = new User(id, name, surname);
            int acctCount = 2 + random.nextInt(9);

            for (int j = 0; j < acctCount; j++) 
            {
                char type = "ABCD".charAt(random.nextInt(4));
                double amt = Math.round(random.nextDouble() * 1000 * 100.0) / 100.0;
                user.addAccount(new Account(type, amt));
            }
            users.add(user);
        }
        originalUsers = new ArrayList<>(users);
        System.out.println("Generating " + n + " random users");
    }

    static String generateId()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    static void listUsers()
    {
        for (User user : users) 
        {
            System.out.printf("%s %s %s Total Amount: %.4f\n", user.id, user.name, user.surname, user.getTotalAmountInCommonCurrency());
        }
    }

    static void showUserById() throws NoSuchElementException
    {
        
            System.out.print("Enter User ID: ");
            String id = scanner.next();
            for (User user : users)
            {
                if (user.id.equals(id)) 
                {
                    System.out.printf("%s %s %s Total Amount: %.4f\n", user.id, user.name, user.surname, user.getTotalAmountInCommonCurrency());
                    System.out.println("Accounts:");
                    int i = 1;
                    for (Account a : user.accounts)
                    {
                        System.out.printf("%d. Type: %c Amount: %.4f Common: %.5f\n", i++, a.type, a.amount, a.getCommon());
                    }
                    return;
                }
            }

            throw new NoSuchElementException("Cannot find the user!");

        
    }

    static void setRates() 
    {
        System.out.print("Set A: "); rateA = scanner.nextDouble();
        System.out.print("Set B: "); rateB = scanner.nextDouble();
        System.out.print("Set C: "); rateC = scanner.nextDouble();
        System.out.print("Set D: "); rateD = scanner.nextDouble();
    }

  
    static void sortUsers() 
    {
        if (users.isEmpty()) 
        {
            System.out.println("No users to sort.");
            return;
        }
        System.out.println("Choose sort type: ");
        System.out.println("1- By ID");
        System.out.println("2- By Total Amount");
        System.out.print("Option: ");

        int type = scanner.nextInt();
        System.out.print("Enter sort limit: ");
        int limit = scanner.nextInt();

        long start = System.currentTimeMillis();
        hybridSort(0, users.size(), limit, type);
        long finish = System.currentTimeMillis();

        System.out.println("Sort duration: " + (finish - start) + " ms");
    }

    static void hybridSort(int from, int to, int limit, int type) 
    {
        if (to - from < limit) 
        {
            insertionSort(from, to, type);
        } 
        else 
        {
            int pivot = partition(from, to, type);
            hybridSort(from, pivot, limit, type);
            hybridSort(pivot + 1, to, limit, type);
        }
    }

    static int partition(int from, int to, int type) 
    {
        User pivot = users.get(from);
        int i = from + 1;
        int j = to - 1;
        while (i <= j) 
        {
            while (i < to && compare(users.get(i), pivot, type) <= 0) 
            {i++;}
            while (j > from && compare(users.get(j), pivot, type) > 0) 
            {j--;}
            if (i < j) 
            {
                swap(i, j);
            }
        }
        swap(from, j);
        return j;
    }

    static void insertionSort(int from, int to, int type) 
    {
        for (int i = from + 1; i < to; i++) 
        {
            User userToCompare = users.get(i);
            int j = i - 1;
            while (j >= from && compare(users.get(j), userToCompare, type) > 0) 
            {
                users.set(j + 1, users.get(j));
                j--;
            }
            users.set(j + 1, userToCompare);
        }
    }

    static int compare(User user1, User user2, int type) {
        if (type == 1) 
        {
            return user1.id.compareTo(user2.id);
        } 
        else 
        {
            double diff = user1.getTotalAmountInCommonCurrency() - user2.getTotalAmountInCommonCurrency();
            if (diff < 0) 
            {
                return -1;
            } 
            else if (diff > 0) 
            {
                return 1;
            } 
            else 
            {
                return 0;
            }
        }
    }

    static void swap(int i, int j) 
    {
        User temp = users.get(i);
        users.set(i, users.get(j));
        users.set(j, temp);
    }

    static void resetUsers() 
    {
        users = new ArrayList<>(originalUsers);
    }
}