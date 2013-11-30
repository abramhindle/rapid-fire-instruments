#!/usr/bin/perl
use strict;
use Net::OpenSoundControl::Client;
use Data::Dumper;
$| = 0;
my $client = Net::OpenSoundControl::Client->new(Host => "127.0.0.1", Port => 57120);
while (<STDIN>) {
    chomp;
    my $line = $_;
    my ($i,$i2,$i3,@parts) = split(/\s+/, $line);
    $client->send(['/goop', ( map { ('f' => $_) } @parts ) ]);
}
