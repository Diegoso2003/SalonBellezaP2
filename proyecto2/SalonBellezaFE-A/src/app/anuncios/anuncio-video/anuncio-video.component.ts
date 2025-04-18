import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-anuncio-video',
  standalone: true,
  imports: [],
  templateUrl: './anuncio-video.component.html',
  styleUrl: './anuncio-video.component.scss'
})
export class AnuncioVideoComponent implements OnInit{

  @Input({ required: true})
  urlYoutube!: string;

  videoId: string = '';
  safeYoutubeUrl!: SafeUrl;

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(){
    this.extraerIdVideo();
    const embedUrl = `https://www.youtube.com/embed/${this.videoId}?autoplay=1&mute=1&loop=1&playlist=${this.videoId}`;
    this.safeYoutubeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }

  private extraerIdVideo(){
    this.videoId = this.urlYoutube.substring(17, 28);
  }
}
