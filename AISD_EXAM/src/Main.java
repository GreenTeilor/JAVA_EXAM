import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main
{
    private static final int P = 1_000_000_007;
    private static final int X = 263;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        HashTable hashTable = new HashTable(scanner.nextInt());
        int operationsCount = scanner.nextInt();
        for (int i = 0; i < operationsCount; i++)
        {
            String operation = scanner.next();
            if (operation.equals("add"))
            {
                hashTable.add(scanner.next());
            }
            else if (operation.equals("del"))
            {
                hashTable.delete(scanner.next());
            }
            else if (operation.equals("find"))
            {
                System.out.println(hashTable.find(scanner.next()));
            }
            else
            {
                System.out.println(hashTable.getValues(scanner.nextInt()));
            }
        }
    }

    private static class HashTable
    {
        Words[] m_table;

        private HashTable(int size)
        {
            m_table = new Words[size];
        }

        private long pow(int pow)
        {
            long result = 1;
            for (int i = 0; i < pow; i++)
                result = (result * X) % P;

            return result;
        }

        private int hashCode(String string)
        {
            long hashCode = 0;
            int i = 0;
            for (char ch : string.toCharArray())
            {
                hashCode = (((hashCode + ((int)ch * pow(i))) % P) + P) % P;
                i++;
            }

            return (int) (hashCode % m_table.length);
        }

        private void add(String string)
        {
            int hashCode = hashCode(string);
            if (m_table[hashCode] == null)
            {
                m_table[hashCode] = new Words();
            }
            m_table[hashCode].add(string);
        }

        private void delete(String string)
        {
            int hashCode = hashCode(string);
            if (m_table[hashCode] != null && !m_table[hashCode].isEmpty())
            {
                m_table[hashCode].delete(string);
            }
        }

        private String find(String string)
        {
            int hashCode = hashCode(string);
            if (m_table[hashCode] == null || m_table[hashCode].isEmpty())
                return "no";
            else
                if (m_table[hashCode].isContain(string))
                    return "yes";
                else
                    return "no";
        }

        private String getValues(int hashCode)
        {
            if (m_table[hashCode] == null || m_table[hashCode].isEmpty())
                return "";
            else
                return m_table[hashCode].getWords();
        }

        private static class Words
        {
            private LinkedList<String> m_wordsList;

            private Words()
            {
                m_wordsList = new LinkedList<>();
            }

            private void add(String string)
            {
                if (!this.isContain(string))
                    m_wordsList.addFirst(string);
            }

            private void delete(String string)
            {
                Iterator<String> iterator = m_wordsList.iterator();
                while (iterator.hasNext())
                    if (iterator.next().equals(string))
                    {
                        iterator.remove();
                        return;
                    }
            }

            private boolean isContain(String string)
            {
                for (String str : m_wordsList)
                    if (str.equals(string))
                        return true;
                return false;
            }

            private String getWords()
            {
                String result = "";
                for (String word : m_wordsList)
                {
                    result += word;
                    result += " ";
                }
                return result;
            }

            private boolean isEmpty()
            {
                return m_wordsList.isEmpty();
            }
        }
    }
}