package  main

import "fmt"

func dfs_canon(D [][]int, q0 int, iter *int, used []bool, canon []int, m int) {
	used[q0] = true
	canon[q0] = *iter
	*iter++
	for j := 0; j < m; j++ {
		if !used[D[q0][j]] {
			dfs_canon(D, D[q0][j], iter, used, canon, m)
		}
	}
}

func main() {
	var n, m, q0, i, j int
	fmt.Scan(&n, &m, &q0)
	D := make([][]int, n)
	for i = 0; i < n; i++ {
		D[i] = make([]int, m)
		for j = 0; j < m; j++ {
			fmt.Scan(&D[i][j])
		}
	}
	F := make([][]string, n)
	for i = 0; i < n; i++ {
		F[i] = make([]string, m)
		for j = 0; j < m; j++ {
			fmt.Scan(&F[i][j])
		}
	}

	iter := 0
	canon := make([]int, n)
	used := make([]bool, n)
	dfs_canon(D, q0, &iter, used, canon, m)

	for i = 0; i < n; i++ {
		for j = 0; j < m; j++ {
			D[i][j] = canon[D[i][j]]
		}
	}
	newF := make([][]string, n)
	newD := make([][]int, n)
	for i = 0; i < n; i++ {
		newD[canon[i]] = D[i]
		newF[canon[i]] = F[i]
	}

	fmt.Printf("%d\n%d\n%d\n", iter, m, 0)
	for i = 0; i < iter; i++ {
		for j = 0; j < m; j++ {
			fmt.Printf("%d ", newD[i][j])
		}
		fmt.Printf("\n")
	}
	for i = 0; i < iter; i++ {
		for j = 0; j < m; j++ {
			fmt.Printf("%s ", newF[i][j])
		}
		fmt.Printf("\n")
	}
}
/*
3
2
0
1 2
1 1
0 2
y x
x y
x y

*/