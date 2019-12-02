package  main

import "fmt"

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
	fmt.Printf("digraph {\n")
	fmt.Printf("\t rankdir = LR\n")
	fmt.Printf("\t dummy [label = \"\", shape = none]\n")
	for i = 0; i < n; i++ {
		fmt.Printf("\t %d [shape = circle] \n", i)
	}
	fmt.Printf("\t dummy -> %d \n", q0)
	for i = 0; i < n; i++ {
		for j = 0; j < m; j++ {
			fmt.Printf("\t %d -> %d [label = \"%c(%s)\"]\n", i, D[i][j], 'a'+j, F[i][j])
		}
	}
	fmt.Printf("}")
}
