# KAKTUS : Java Neural Network Density Functional Theory for Molecule
KAKTUD is a Java program to solve the KSDFT equation using the Gaussian function to get a total energy of molecules.
## Features
- SCF with electron density mixing
- Hartree-Fock
- DFT: Neural Network and LDA functionals
- XC Neural Network inversion
## Requirements
- Linux OS
- Java (https://www.java.com/en/download/) version >= 11
## Inversion ANN Example

------------
    {
	"name": ["H2S,S,H,H}", "Na2,Na,Na}", "H2O2,H,H,O,O}"],
	"con": 0.0015936254980079682,
	"re": [
		[-0.27601593625498005],
		[-0.026454183266932274],
		[-0.4020717131474104]
	],
	"simpul": [1, 2, 2, 1],
	"name_ann": "ann_new_dft"
}
------------

## Calculation Total Energy Example

------------
	H2O;
	Water;
	{"xyz": 
	{"1": [0.0000, 0.0000, 0.1173], 
	"2": [0.0000, 0.7572, -0.4692],
	"3": [0.0000, -0.7572, -0.4692]}, 
	"atom": ["O", "H","H"],
	"Spin_dn": 5, "Spin_up": 5};
------------
