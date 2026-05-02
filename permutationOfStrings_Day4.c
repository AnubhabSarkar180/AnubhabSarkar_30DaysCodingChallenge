#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void swap(char **s, int i, int j) {
    char *temp = s[i];
    s[i] = s[j];
    s[j] = temp;
}

void reverse(char **s, int start, int end) {
    while (start < end) {
        swap(s, start++, end--);
    }
}


int next_permutation(int n, char **s)
{
	/**
	* Complete this method
	* Return 0 when there is no next permutation and 1 otherwise
	* Modify array s to its next permutation
	*/
    int i = n - 2;
    while (i >= 0 && strcmp(s[i], s[i + 1]) >= 0) {
        i--;
    }
    if (i < 0) return 0;
    
    int j = n - 1;
    while (strcmp(s[j], s[i]) <= 0) {
        j--;
    }
    swap(s, i, j);
    reverse(s, i + 1, n - 1);

    return 1;
    
}