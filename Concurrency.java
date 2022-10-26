
/**
 * 
 */

/**
 * @author gbrown62
 *
 */
import java.util.Random;

class addmation extends Thread {

	private int[] gab;

	private int small, big, partly;

	public addmation(int[] gab, int small, int big)

	{
		//Range of values to sum-up
		this.gab = gab;
		this.small = small;
		this.big = Math.min(big, gab.length);

	}

	public int getpartlyadd()

	{

		return partly;

	}

	public void run()

	{

		partly = add(gab, small, big);

	}

	public static int add(int[] gab)

	{

		return add(gab, 0, gab.length);

	}

	public static int add(int[] gab, int small, int big)

	{

		int result = 0;

		for (int i = small; i < big; i++) {

			result += gab[i];

		}

		return result;

	}

	public static int paralleladd(int[] gab)

	{

		return paralleladd(gab, Runtime.getRuntime().availableProcessors());

	}

	public static int paralleladd(int[] gab, int threads)

	{
		
		int scope = (int) Math.ceil(gab.length * 1.0 / threads);

		addmation[] adds = new addmation[threads];
		
		// add in range '0' .. 'to' inclusive of the value 'to'
		for (int i = 0; i < threads; i++) {

			adds[i] = new addmation(gab, i * scope, (i + 1) * scope);

			adds[i].start();

		}

		try {

			for (addmation add : adds) {

				add.join();

			}

		} catch (InterruptedException e) {
		}

		int result = 0;

		for (addmation add : adds) {

			result += add.getpartlyadd();

		}

		return result;

	}

}

public class Concurrency {

	public static void main(String[] args)

	{

		Random chance = new Random();

		int[] gab = new int[200000000];//an array of 200 million random number

		for (int i = 0; i < gab.length; i++) {

			gab[i] = chance.nextInt(10) + 1;

		}

		long start = System.currentTimeMillis();

		System.out.println(addmation.add(gab));

		System.out.println("Single: " + (System.currentTimeMillis() - start)); //the sum with only one thread

		start = System.currentTimeMillis();

		System.out.println(addmation.paralleladd(gab));

		System.out.println("Parallel: " + (System.currentTimeMillis() - start));//the sum in parallel using multiple threads

	}

}