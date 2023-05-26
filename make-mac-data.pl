#!/usr/bin/perl

$ans = `wget https://standards-oui.ieee.org`;
$ans = `cat index.html | grep "base 16" > index_tmp`;

 
open H, 'index_tmp' or die ("unable to open file");
open OUT, '>macs.dat' or die ('unable to open file macs.dat');

while ($line = <H>) {
  chomp $line;

  $line =~ s/ //g;
  $line =~ s/\t//gi;
  @parts = split(/\(base16\)/, $line);

  print OUT $parts[0] . ',' . $parts[1] . "\n";
}

unlink("index.html");
unlink("index_tmp");

close OUT;
close H;
